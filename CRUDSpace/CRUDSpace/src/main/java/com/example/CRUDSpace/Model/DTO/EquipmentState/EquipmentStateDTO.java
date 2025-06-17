package com.example.CRUDSpace.Model.DTO.EquipmentState;

import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentDTO;
import com.example.CRUDSpace.Model.DTO.Value.ValueDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EquipmentStateDTO {

    UUID equipmentStateId;

    EquipmentDTO equipment;

    ValueDTO value;

    String timeStamp;

    String valueResponse;
}
