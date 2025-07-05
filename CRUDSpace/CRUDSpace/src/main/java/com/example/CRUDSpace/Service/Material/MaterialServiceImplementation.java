package com.example.CRUDSpace.Service.Material;

import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;
import com.example.CRUDSpace.Model.DTO.Material.MaterialCreationDTO;
import com.example.CRUDSpace.Model.Entity.Material;
import com.example.CRUDSpace.Model.Entity.MaterialType;
import com.example.CRUDSpace.Repository.MaterialRepository;
import com.example.CRUDSpace.Repository.MaterialTypeRepository;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialServiceImplementation implements MaterialServiceInterface {

    MaterialRepository materialRepository;
    MaterialTypeRepository materialTypeRepository;

    public String addMaterial(MaterialCreationDTO dto) {
        if (materialRepository.existsByMaterialNameAndMaterialTypeId(dto.getMaterialName(), dto.getMaterialTypeId())) {
            throw new AppException(ErrorCode.MATERIAL_ALREADY_EXIST);
        }

        MaterialType materialType = materialTypeRepository.findById(dto.getMaterialTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_TYPE_NOT_FOUND));

        Material newMaterial = Material.builder()
                .materialName(dto.getMaterialName())
                .materialType(materialType)
                .build();

        materialRepository.save(newMaterial);

        return "Add new material Successfully!";
    }
}
