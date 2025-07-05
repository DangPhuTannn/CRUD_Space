package com.example.CRUDSpace.Model.DTO.MaterialUnits;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialUnitsCreationFromInventoryDTO {

    @NotNull(message = "FIELD_REQUIRED")
    UUID materialId;

    @NotNull(message = "FIELD_REQUIRED")
    UUID spaceId;

    @Positive(message = "FIELD_POSITIVE")
    int quantity;

    @NotNull(message = "FIELD_REQUIRED")
    UUID inventoryId;
}
