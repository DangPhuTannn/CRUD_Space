package com.example.CRUDSpace.Service.MaterialUnits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;
import com.example.CRUDSpace.Model.DTO.MaterialUnits.MaterialUnitsCreationFromInventoryDTO;
import com.example.CRUDSpace.Model.DTO.MaterialUnits.MaterialUnitsCreationFromSupplierDTO;
import com.example.CRUDSpace.Model.Entity.Material;
import com.example.CRUDSpace.Model.Entity.MaterialUnits;
import com.example.CRUDSpace.Model.Entity.Space;
import com.example.CRUDSpace.Repository.MaterialRepository;
import com.example.CRUDSpace.Repository.MaterialUnitsRepository;
import com.example.CRUDSpace.Repository.SpaceRepository;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class MaterialUnitsServiceImplementation implements MaterialUnitsServiceInterface {

    MaterialUnitsRepository materialUnitsRepository;
    MaterialRepository materialRepository;
    SpaceRepository spaceRepository;

    // thêm material units cho inventory, ko add cho những space khác
    public String addMaterialUnitsFromSupplier(MaterialUnitsCreationFromSupplierDTO dto) {

        Space space = spaceRepository.findBySpaceIdWithType(dto.getSpaceId())
                .orElseThrow(() -> new AppException(ErrorCode.SPACE_NOT_FOUND));

        if (!space.getType().getTypeName().equals("Inventory")) {
            throw new AppException(ErrorCode.ONLY_FOR_INVENTORY);
        }

        Material material = materialRepository.findByMaterialId(dto.getMaterialId())
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));

        List<Space> parentSpaces = spaceRepository.findAllParentSpaces(space.getParentId());
        List<UUID> parentSpaceIds = parentSpaces.stream().map(Space::getSpaceId).toList();
        List<Space> inventories = spaceRepository.findAllChildrenInventoryBySpaceIds(parentSpaceIds);

        Map<UUID, Space> parentSpacesMap = parentSpaces.stream()
                .collect(Collectors.toMap(Space::getSpaceId, eachSpace -> eachSpace));

        Map<UUID, Space> inventoriesMap = inventories.stream()
                .collect(Collectors.toMap(Space::getParentId, eachSpace -> eachSpace));

        List<UUID> spaceIds = inventories.stream()
                .map(Space::getSpaceId)
                .toList();

        parentSpacesMap.forEach((spaceId, parentSpace) -> {
            Space getThisSpace = inventoriesMap.getOrDefault(spaceId, null);
            if (getThisSpace == null) {
                spaceIds.add(spaceId);
            }
        });

        materialUnitsRepository.incrementUnitsFromChildren(dto.getMaterialId(), spaceIds, dto.getQuantity());

        List<Space> spacesWithNoMaterial = spaceRepository.findAllSpacesWithoutSpecificMaterial(spaceIds,
                dto.getMaterialId());

        List<MaterialUnits> materialUnits = new ArrayList<>();

        for (Space s : spacesWithNoMaterial) {
            MaterialUnits mu = MaterialUnits.builder()
                    .availableUnits(0)
                    .totalUnits(0)
                    .unitsFromChildren(dto.getQuantity())
                    .material(material)
                    .space(s)
                    .build();
            materialUnits.add(mu);
        }

        materialUnitsRepository.saveAll(materialUnits);
        return addMaterialUnits(material, space, dto.getQuantity());

    }

    // add material units cho space từ inventory
    public String addMaterialUnitsFromInventory(MaterialUnitsCreationFromInventoryDTO dto) {

        Space space = spaceRepository.findBySpaceIdWithType(dto.getSpaceId())
                .orElseThrow(() -> new AppException(ErrorCode.SPACE_NOT_FOUND));
        if (space.getType().getTypeName().equals("Inventory")) {
            throw new AppException(ErrorCode.NOT_FOR_INVENTORY);
        }

        Material material = materialRepository.findByMaterialId(dto.getMaterialId())
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));

        MaterialUnits materialUnitsFromInventory = materialUnitsRepository
                .findByMaterialIdAndSpaceId(dto.getMaterialId(), dto.getInventoryId())
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));

        if (materialUnitsFromInventory.getAvailableUnits() < dto.getQuantity()) {
            throw new AppException(ErrorCode.OVER_AVAILABLE_UNITS);
        }

        materialUnitsFromInventory.setAvailableUnits(
                materialUnitsFromInventory.getAvailableUnits() - dto.getQuantity());

        return addMaterialUnits(material, space, dto.getQuantity());

    }

    // tiến hành thêm material units cho space bất kì
    private String addMaterialUnits(Material material, Space space, int quantity) {
        MaterialUnits materialUnits = materialUnitsRepository
                .findByMaterialIdAndSpaceId(material.getMaterialId(), space.getSpaceId())
                .orElseGet(() -> null);

        if (materialUnits != null) {
            materialUnitsRepository.incrementAvailableAndTotalUnits(materialUnits.getMaterialUnitsId(), quantity);
        } else {
            materialUnits = MaterialUnits.builder()
                    .availableUnits(quantity)
                    .totalUnits(quantity)
                    .material(material)
                    .space(space)
                    .build();
            materialUnitsRepository.save(materialUnits);
        }

        return "Add New Material Units Successfully!";
    }
}
