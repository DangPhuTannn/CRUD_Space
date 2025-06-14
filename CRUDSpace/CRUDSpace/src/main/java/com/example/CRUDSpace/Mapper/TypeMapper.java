package com.example.CRUDSpace.Mapper;

import org.mapstruct.Mapper;

import com.example.CRUDSpace.Model.DTO.Type.TypeCreationDTO;
import com.example.CRUDSpace.Model.DTO.Type.TypeDTO;
import com.example.CRUDSpace.Model.Entity.Type;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    TypeDTO toTypeDTO(Type type);

    Type toType(TypeCreationDTO dto);
}
