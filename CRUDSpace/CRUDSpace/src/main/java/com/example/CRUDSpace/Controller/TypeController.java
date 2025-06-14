package com.example.CRUDSpace.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;
import com.example.CRUDSpace.Model.DTO.Type.TypeCreationDTO;
import com.example.CRUDSpace.Model.DTO.Type.TypeDTO;
import com.example.CRUDSpace.Service.Type.TypeServiceInterface;

import lombok.*;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeController {

    TypeServiceInterface typeService;

    @GetMapping
    ApiResponse<List<TypeDTO>> getAllTypes() {
        var result = typeService.getAllTypes();

        return ApiResponse.<List<TypeDTO>>builder().result(result).build();
    }

    @GetMapping("{typeId}")
    ApiResponse<TypeDTO> getTypeById(UUID typeId) {
        var result = typeService.getTypeById(typeId);

        return ApiResponse.<TypeDTO>builder().result(result).build();
    }

    @PostMapping
    ApiResponse<String> createType(@RequestBody TypeCreationDTO dto) {
        var result = typeService.createType(dto);

        return ApiResponse.<String>builder().result(result).build();
    }
}
