package com.example.CRUDSpace.Model.DTO.Equipment;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EquipmentDTO {

    UUID equipmentId;

    String equipmentName;
}
