package com.example.CRUDSpace.Model.DTO.QEnergy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QEnergyDTO {

    UUID qEnergyId;

    BigDecimal currentEnergyConsumption;

    BigDecimal totalEnergyConsumption;

    LocalDateTime date;
}
