package com.example.CRUDSpace.Service.Equipment;

import java.util.List;
import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentDTO;
import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentWithAllRelationsDTO;

public interface EquipmentServiceInterface {

    EquipmentDTO getEquipmentById(UUID equipmentId);

    List<EquipmentDTO> getAllEquipments();

    List<EquipmentWithAllRelationsDTO> getAllEquipmentWithSpace();
}
