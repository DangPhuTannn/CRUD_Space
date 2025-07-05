package com.example.CRUDSpace.Service.Space;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;
import com.example.CRUDSpace.Mapper.SpaceMapper;
import com.example.CRUDSpace.Model.DTO.Material.MaterialAddingForSpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.MaterialsInSpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithChildrenDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithTypeDTO;
import com.example.CRUDSpace.Model.Entity.MaterialUnits;
import com.example.CRUDSpace.Model.Entity.Space;
import com.example.CRUDSpace.Model.Entity.Type;
import com.example.CRUDSpace.Repository.MaterialUnitsRepository;
import com.example.CRUDSpace.Repository.SpaceRepository;
import com.example.CRUDSpace.Repository.TypeRepository;
import com.example.CRUDSpace.utils.SpaceUtils;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpaceServiceImplementation implements SpaceServiceInterface {

    SpaceRepository spaceRepository;
    SpaceMapper spaceMapper;
    SpaceUtils spaceUtils;
    TypeRepository typeRepository;
    MaterialUnitsRepository materialUnitsRepository;

    public List<SpaceDTO> getAllSpaces() {
        List<Space> spaces = spaceRepository.findAll();

        List<SpaceDTO> spacesDTO = spaces.stream().map(eachSpace -> spaceMapper.toSpaceDTO(eachSpace)).toList();

        return spacesDTO;
    }

    public SpaceDTO getSpaceById(UUID spaceId) {

        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new AppException(ErrorCode.SPACE_NOT_FOUND));

        return spaceMapper.toSpaceDTO(space);
    }

    public List<SpaceWithChildrenDTO> getAllSpacesWithChildren() {

        List<Space> spaces = spaceRepository.findAll();

        Map<UUID, SpaceWithChildrenDTO> spaceDTOMap = spaceUtils.buildSpaceWithChildrenDTOMap(spaces);

        List<SpaceWithChildrenDTO> highestLevelSpaceWithChildrenDTOs = spaceUtils
                .getHighestLevelSpaceWithChildrenDTOs(spaceDTOMap, spaces);

        return highestLevelSpaceWithChildrenDTOs;
    }

    public SpaceWithTypeDTO getSpaceWithTypeById(UUID spaceId) {
        SpaceWithTypeDTO space = spaceRepository.findSpaceWithTypeDTOById(spaceId)
                .orElseThrow(() -> new AppException(ErrorCode.SPACE_NOT_FOUND));
        return space;
    }

    public List<SpaceWithTypeDTO> getAllSpacesWithType() {
        List<SpaceWithTypeDTO> spaces = spaceRepository.getAllSpaceWithTypeDTO();
        return spaces;
    }

    // Khi nhập hàng từ bên ngoài vào 1 space, nếu space này
    // chưa có kho thì sẽ tạo kho mới
    public String addInventoryBySpaceId(UUID spaceId) {

        // Tìm những children space có type là inventory để check maximum
        List<Space> childrenSpaces = spaceRepository.findChildrenInventoryByParentSpaceId(spaceId);

        // đạt max
        if (childrenSpaces.size() > 0) {
            throw new AppException(ErrorCode.SPACE_ALREADY_HAS_MAX_CHILDREN);
        }

        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new AppException(ErrorCode.SPACE_NOT_FOUND));

        Type inventoryType = typeRepository.findByTypeName("Inventory")
                .orElseThrow(() -> new AppException(ErrorCode.TYPE_NOT_FOUND));

        Space newInventory = Space.builder()
                .parentId(spaceId)
                .spaceName("Inventory - " + space.getSpaceName())
                .type(inventoryType)
                .build();

        spaceRepository.save(newInventory);

        // Nếu space hiện tại đã có material units, do những space con của space
        // hiện tại
        /// đã có inventory, thì cần chuyển những material units đó sang
        // inventory mới
        List<MaterialUnits> materialUnits = materialUnitsRepository.findAllBySpaceId(spaceId);
        if (!materialUnits.isEmpty()) {
            for (MaterialUnits materialUnit : materialUnits) {
                materialUnit.setSpace(newInventory);
            }
            materialUnitsRepository.saveAll(materialUnits);
        }

        return "Add New Inventory Successfully!";
    }

    // mỗi khi muốn add material cho 1 space bất kì khác inventory
    // nếu là inventory thì chỉ cần lấy material thôi, cho ng dùng tự chọn
    public List<MaterialsInSpaceDTO> getAllMaterialsInInventory(UUID spaceId) {

        Space space = spaceRepository.findBySpaceIdWithType(spaceId)
                .orElseThrow(() -> new AppException(ErrorCode.SPACE_NOT_FOUND));

        if (space.getType().getTypeName().equals("Inventory")) {
            throw new AppException(ErrorCode.NOT_FOR_INVENTORY);
        }

        // lấy tất cả inventory của space hiện tại
        List<Space> childInventories = spaceRepository.findChildrenInventoryByParentSpaceId(spaceId);

        List<Object[]> rawResults;

        // nếu space hiện tại không có inventory con, thì lấy tất cả inventory
        // của space cha
        if (childInventories == null || childInventories.isEmpty()) {
            rawResults = spaceRepository.findInventoryMaterialsInChildrenOfAncestors(spaceId);
        } else {
            // nếu có thì lấy ra
            List<UUID> inventoryIds = childInventories.stream()
                    .map(Space::getSpaceId)
                    .collect(Collectors.toList());

            rawResults = spaceRepository.findMaterialsByInventoryIds(inventoryIds);
        }

        Map<UUID, MaterialsInSpaceDTO> resultMap = new LinkedHashMap<>();

        for (Object[] row : rawResults) {
            UUID parentSpaceId = row[0] instanceof UUID
                    ? (UUID) row[0]
                    : UUID.fromString((String) row[0]);

            String spaceName = (String) row[1];

            UUID materialId = row[2] instanceof UUID
                    ? (UUID) row[2]
                    : UUID.fromString((String) row[2]);
            String materialName = (String) row[3];
            int availableUnits = (int) row[4];
            UUID materialTypeId = row[5] instanceof UUID
                    ? (UUID) row[5]
                    : UUID.fromString((String) row[5]);
            String materialTypeName = (String) row[6];
            String measurement = (String) row[7];

            MaterialsInSpaceDTO dto = resultMap.computeIfAbsent(parentSpaceId, id -> {
                MaterialsInSpaceDTO m = new MaterialsInSpaceDTO();
                m.setSpaceId(id);
                m.setSpaceName(spaceName);
                return m;
            });

            dto.getMaterials().add(
                    MaterialAddingForSpaceDTO.builder()
                            .materialId(materialId)
                            .materialName(materialName)
                            .availableUnits(availableUnits)
                            .materialTypeId(materialTypeId)
                            .materialTypeName(materialTypeName)
                            .measurement(measurement)
                            .build());
        }

        return new ArrayList<>(resultMap.values());

    }

}
