package com.example.CRUDSpace.Service.Type;

import java.util.List;
import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Type.TypeCreationDTO;
import com.example.CRUDSpace.Model.DTO.Type.TypeDTO;

public interface TypeServiceInterface {

    TypeDTO getTypeById(UUID typeId);

    List<TypeDTO> getAllTypes();

    String createType(TypeCreationDTO dto);
}
