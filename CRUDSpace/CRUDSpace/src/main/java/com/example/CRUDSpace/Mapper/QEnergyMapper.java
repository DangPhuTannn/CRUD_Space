package com.example.CRUDSpace.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.CRUDSpace.Model.DTO.QEnergy.QEnergyDTO;
import com.example.CRUDSpace.Model.Entity.QEnergy;

@Mapper(componentModel = "spring")
public interface QEnergyMapper {

    @Mapping(source = "QEnergyId", target = "qEnergyId")
    QEnergyDTO toQEnergyDTO(QEnergy q);

}
