package com.example.CRUDSpace.Service.EquipmentState;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Model.DTO.EquipmentState.EquipmentStateDTO;
import com.example.CRUDSpace.Repository.EquipmentStateRepository;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EquipmentStateImplementation implements EquipmentStateInterface {

    EquipmentStateRepository equipmentStateRepository;

    public List<EquipmentStateDTO> getAllEquipmentState() {
        return equipmentStateRepository.getAllEquipmentState();
    }

    public List<EquipmentStateDTO> getAllEquipmentStateByEquipmentId(UUID equipmentId) {
        return equipmentStateRepository.getAllEquipmentStateByEquipmentId(equipmentId);
    }

}
