package com.example.CRUDSpace.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;
import com.example.CRUDSpace.Model.DTO.Material.MaterialCreationDTO;
import com.example.CRUDSpace.Service.Material.MaterialServiceInterface;

import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/material")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialController {

    MaterialServiceInterface materialService;

    @PostMapping
    ApiResponse<String> addMaterial(@Valid @RequestBody MaterialCreationDTO dto) {
        var result = materialService.addMaterial(dto);

        return ApiResponse.<String>builder()
                .result(result)
                .build();
    }

}
