package com.example.CRUDSpace.Service.EquipmentState;

import java.util.List;
import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.EquipmentState.EquipmentStateDTO;

public interface EquipmentStateInterface {

    List<EquipmentStateDTO> getAllEquipmentState();

    List<EquipmentStateDTO> getAllEquipmentStateByEquipmentId(UUID equipmentId);
}
