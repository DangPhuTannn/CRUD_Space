package com.example.CRUDSpace.Service.Equipment;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;
import com.example.CRUDSpace.Mapper.EquipmentMapper;
import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentDTO;
import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentWithSpaceDTO;
import com.example.CRUDSpace.Model.Entity.Equipment;
import com.example.CRUDSpace.Repository.EquipmentRepository;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EquipmentServiceImplementation implements EquipmentServiceInterface {

    EquipmentRepository equipmentRepository;
    EquipmentMapper equipmentMapper;

    public EquipmentDTO getEquipmentById(UUID equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));

        return equipmentMapper.toEquipmentDTO(equipment);
    }

    public List<EquipmentDTO> getAllEquipments() {
        List<Equipment> equipments = equipmentRepository.findAll();

        List<EquipmentDTO> equipmentDTOs = equipments.stream()
                .map(eachEquipment -> equipmentMapper.toEquipmentDTO(eachEquipment)).toList();

        return equipmentDTOs;
    }

    public List<EquipmentWithSpaceDTO> getAllEquipmentWithSpace() {
        List<EquipmentWithSpaceDTO> equipments = equipmentRepository.getAllEquipmentWithSpace();

        return equipments;
    }
}
