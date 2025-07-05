package com.example.CRUDSpace.Service.Space;

import java.util.List;
import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Space.MaterialsInSpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithChildrenDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithTypeDTO;

public interface SpaceServiceInterface {

    List<SpaceDTO> getAllSpaces();

    SpaceDTO getSpaceById(UUID spaceId);

    List<SpaceWithChildrenDTO> getAllSpacesWithChildren();

    SpaceWithTypeDTO getSpaceWithTypeById(UUID spaceId);

    List<SpaceWithTypeDTO> getAllSpacesWithType();

    String addInventoryBySpaceId(UUID spaceId);

    List<MaterialsInSpaceDTO> getAllMaterialsInInventory(UUID spaceId);
}
