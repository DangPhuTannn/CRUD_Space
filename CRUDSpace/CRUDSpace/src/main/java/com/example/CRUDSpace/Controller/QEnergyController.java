package com.example.CRUDSpace.Controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;
import com.example.CRUDSpace.Model.DTO.QEnergy.QEnergyDTO;
import com.example.CRUDSpace.Model.DTO.QEnergy.QEneryTotalConsumptionDateRangeDTO;
import com.example.CRUDSpace.Service.Qenergy.QEnergyServiceImplementation;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/qenergy")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QEnergyController {

    QEnergyServiceImplementation qenergyService;

    @GetMapping("/{spaceId}/currentEnergyConsumption")
    ApiResponse<QEnergyDTO> getCurrentEnergyConsumptionBySpaceId(@PathVariable UUID spaceId) {
        var result = qenergyService.getEnergyConsumptionBySpaceId(spaceId);
        return ApiResponse.<QEnergyDTO>builder()
                .result(result)
                .build();
    }
    
    @PostMapping("/totalEnergyConsumption")
    ApiResponse<BigDecimal> getTotalEnergyConsumptionBySpaceIdAndDateRange(
            @Valid @RequestBody QEneryTotalConsumptionDateRangeDTO dto) {
        var result = qenergyService.getTotalEnergyConsumptionBySpaceIdAndDateRange(dto);

        return ApiResponse.<BigDecimal>builder()
                .result(result)
                .build();
    }

    
}
