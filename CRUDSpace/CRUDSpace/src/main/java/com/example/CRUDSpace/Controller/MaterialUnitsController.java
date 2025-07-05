package com.example.CRUDSpace.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;
import com.example.CRUDSpace.Model.DTO.MaterialUnits.MaterialUnitsCreationFromSupplierDTO;
import com.example.CRUDSpace.Service.MaterialUnits.MaterialUnitsServiceInterface;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/materialUnits")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialUnitsController {

    MaterialUnitsServiceInterface materialUnitsService;

    @PostMapping("/addFromSupplier")
    ApiResponse<String> addMaterialUnitsFromSupplier(
            @Valid @RequestBody MaterialUnitsCreationFromSupplierDTO dto) {
        var result = materialUnitsService.addMaterialUnitsFromSupplier(dto);

        return ApiResponse.<String>builder()
                .result(result)
                .build();
    }
}
