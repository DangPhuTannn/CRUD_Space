package com.example.CRUDSpace.Model.DTO.Equipment;

import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Provider.ProviderDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EquipmentWithAllRelationsDTO {

    UUID equipmentId;

    String equipmentName;

    SpaceDTO space;

    ProviderDTO provider;
}
