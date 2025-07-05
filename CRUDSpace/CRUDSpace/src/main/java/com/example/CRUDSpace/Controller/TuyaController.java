package com.example.CRUDSpace.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;
import com.example.CRUDSpace.Model.DTO.Tuya.DeviceProperties;
import com.example.CRUDSpace.Model.DTO.Tuya.TuyaSpaceDTO;
import com.example.CRUDSpace.Service.Tuya.TuyaServiceInterface;

@RestController
@RequestMapping("/api/tuya")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TuyaController {

    TuyaServiceInterface tuyaService;

    @GetMapping("/device/{deviceId}/properties")
    ApiResponse<List<DeviceProperties>> getDevicePropertiesById(@PathVariable String deviceId) throws Exception {
        var result = tuyaService.getDevicePropertiesById(deviceId);
        return ApiResponse.<List<DeviceProperties>>builder().result(result).build();
    }

    @GetMapping("/space/{spaceId}")
    ApiResponse<TuyaSpaceDTO> getSpaceById(@PathVariable String spaceId) throws Exception {
        var result = tuyaService.getSpaceById(spaceId);

        return ApiResponse.<TuyaSpaceDTO>builder().result(result).build();
    }
}