package com.example.CRUDSpace.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;
import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentDTO;
import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentWithAllRelationsDTO;
import com.example.CRUDSpace.Service.Equipment.EquipmentServiceInterface;

import lombok.*;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EquipmentController {

    EquipmentServiceInterface equipmentService;

    @GetMapping
    ApiResponse<List<EquipmentDTO>> getAllEquipments() {
        var result = equipmentService.getAllEquipments();

        return ApiResponse.<List<EquipmentDTO>>builder().result(result).build();
    }

    @GetMapping("{equipmentId}")
    ApiResponse<EquipmentDTO> getEquipmentById(@PathVariable UUID equipmentId) {
        var result = equipmentService.getEquipmentById(equipmentId);

        return ApiResponse.<EquipmentDTO>builder().result(result).build();
    }

    @GetMapping("/equipmentWithAllRelations")
    ApiResponse<List<EquipmentWithAllRelationsDTO>> getAllEquipmentWithSpace() {
        var result = equipmentService.getAllEquipmentWithSpace();

        return ApiResponse.<List<EquipmentWithAllRelationsDTO>>builder().result(result).build();
    }

}
