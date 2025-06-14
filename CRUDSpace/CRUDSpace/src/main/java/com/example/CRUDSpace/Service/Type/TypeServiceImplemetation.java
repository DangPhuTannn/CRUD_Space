package com.example.CRUDSpace.Service.Type;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;
import com.example.CRUDSpace.Mapper.TypeMapper;
import com.example.CRUDSpace.Model.DTO.Type.TypeCreationDTO;
import com.example.CRUDSpace.Model.DTO.Type.TypeDTO;
import com.example.CRUDSpace.Model.Entity.Type;
import com.example.CRUDSpace.Repository.TypeRepository;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeServiceImplemetation implements TypeServiceInterface {

    TypeRepository typeRepository;
    TypeMapper typeMapper;

    public TypeDTO getTypeById(UUID typeId) {
        Type type = typeRepository.findById(typeId).orElseThrow(() -> new AppException(ErrorCode.TYPE_NOT_FOUND));

        return typeMapper.toTypeDTO(type);
    }

    public List<TypeDTO> getAllTypes() {
        List<Type> types = typeRepository.findAll();

        return types.stream().map(eachType -> typeMapper.toTypeDTO(eachType)).toList();
    }

    public String createType(TypeCreationDTO dto) {

        if (typeRepository.existsByLevel(dto.getLevel())) {
            throw new AppException(ErrorCode.TYPE_LEVEL);
        }

        if (typeRepository.existsByTypeName(dto.getTypeName())) {
            throw new AppException(ErrorCode.TYPE_NAME);
        }

        Type type = typeMapper.toType(dto);

        typeRepository.save(type);

        return "Create Type Successfully!!";
    }

}
