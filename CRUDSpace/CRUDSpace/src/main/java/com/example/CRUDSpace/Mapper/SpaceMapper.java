package com.example.CRUDSpace.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.CRUDSpace.Model.DTO.Space.SpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithChildrenDTO;
import com.example.CRUDSpace.Model.Entity.Space;

@Mapper(componentModel = "spring")
public interface SpaceMapper {

    SpaceDTO toSpaceDTO(Space space);

    @Mapping(target = "childrenSpaces", ignore = true)
    SpaceWithChildrenDTO toSpaceWithChildrenDTO(Space space);
}
