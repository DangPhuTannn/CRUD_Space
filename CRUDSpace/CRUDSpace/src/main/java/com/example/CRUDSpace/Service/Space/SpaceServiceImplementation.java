package com.example.CRUDSpace.Service.Space;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;
import com.example.CRUDSpace.Mapper.SpaceMapper;
import com.example.CRUDSpace.Model.DTO.Space.SpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithChildrenDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithTypeDTO;
import com.example.CRUDSpace.Model.Entity.Space;
import com.example.CRUDSpace.Repository.SpaceRepository;
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

}
