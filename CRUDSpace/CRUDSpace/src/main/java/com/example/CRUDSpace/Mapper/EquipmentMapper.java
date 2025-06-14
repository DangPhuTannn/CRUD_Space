package com.example.CRUDSpace.Mapper;

import org.mapstruct.Mapper;

import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentDTO;
import com.example.CRUDSpace.Model.Entity.Equipment;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {

    EquipmentDTO toEquipmentDTO(Equipment equipment);
}
