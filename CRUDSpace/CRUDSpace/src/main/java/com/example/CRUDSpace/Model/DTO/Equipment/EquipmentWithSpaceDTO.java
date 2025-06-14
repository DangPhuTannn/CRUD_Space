package com.example.CRUDSpace.Model.DTO.Equipment;

import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Space.SpaceDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EquipmentWithSpaceDTO {

    UUID equipmentId;

    String equipmentName;

    SpaceDTO space;
}
