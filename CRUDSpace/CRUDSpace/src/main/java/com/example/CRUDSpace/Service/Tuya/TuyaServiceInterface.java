package com.example.CRUDSpace.Service.Tuya;

import java.util.List;
import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Tuya.DeviceProperties;
import com.example.CRUDSpace.Model.DTO.Tuya.TuyaSpaceDTO;

public interface TuyaServiceInterface {
    TuyaSpaceDTO getSpaceById(String spaceId) throws Exception;

    List<DeviceProperties> getDevicePropertiesById(String deviceId) throws Exception;
}
