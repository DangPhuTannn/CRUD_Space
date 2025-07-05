package com.example.CRUDSpace.Mapper;

import org.mapstruct.Mapper;

import com.example.CRUDSpace.Model.Entity.EquipmentState;
import com.example.CRUDSpace.Model.Entity.StateHistory;

@Mapper(componentModel = "spring")
public interface StateHistoryMapper {

    StateHistory toStateHistory(EquipmentState equipmentState);
}
