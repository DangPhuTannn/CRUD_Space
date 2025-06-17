package com.example.CRUDSpace.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;
import com.example.CRUDSpace.Model.DTO.EquipmentState.EquipmentStateDTO;
import com.example.CRUDSpace.Service.EquipmentState.EquipmentStateInterface;

import lombok.*;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/equipmentState")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EquipmentStateController {
    EquipmentStateInterface equipmentStateInterface;

    @GetMapping
    ApiResponse<List<EquipmentStateDTO>> getAllEquipmentState() {
        var result = equipmentStateInterface.getAllEquipmentState();
        return ApiResponse.<List<EquipmentStateDTO>>builder()
                .result(result)
                .build();
    }

    @GetMapping("{equipmentId}")
    ApiResponse<List<EquipmentStateDTO>> getAllEquipmentStateByEquipmentId(@PathVariable UUID equipmentId) {
        var result = equipmentStateInterface.getAllEquipmentStateByEquipmentId(equipmentId);
        return ApiResponse.<List<EquipmentStateDTO>>builder()
                .result(result)
                .build();
    }
}
