package com.example.CRUDSpace.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.CRUDSpace.Mapper.SpaceMapper;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithChildrenDTO;
import com.example.CRUDSpace.Model.Entity.Space;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpaceUtils {

    SpaceMapper spaceMapper;

    public Map<UUID, SpaceWithChildrenDTO> buildSpaceWithChildrenDTOMap(List<Space> spaces) {
        Map<UUID, SpaceWithChildrenDTO> spaceDTOMap = new HashMap<>();

        for (Space eachSpace : spaces) {
            spaceDTOMap.put(eachSpace.getSpaceId(), spaceMapper.toSpaceWithChildrenDTO(eachSpace));
        }

        return spaceDTOMap;
    }

    public List<SpaceWithChildrenDTO> getHighestLevelSpaceWithChildrenDTOs(
            Map<UUID, SpaceWithChildrenDTO> spaceWithChildrenDTOMap, List<Space> spaces) {
        List<SpaceWithChildrenDTO> highestLevelSpaceWithChildrenDTOs = new ArrayList<>();

        for (Space eachSpace : spaces) {
            SpaceWithChildrenDTO currentSpaceDTO = spaceWithChildrenDTOMap.get(eachSpace.getSpaceId());
            if (eachSpace.getParentId() == null) {
                highestLevelSpaceWithChildrenDTOs.add(currentSpaceDTO);
            } else {
                SpaceWithChildrenDTO parentCurrentSpace = spaceWithChildrenDTOMap.getOrDefault(eachSpace.getParentId(),
                        null);
                if (parentCurrentSpace != null) {
                    parentCurrentSpace.getChildrenSpaces().add(currentSpaceDTO);
                }
            }
        }

        return highestLevelSpaceWithChildrenDTOs;
    }
}
