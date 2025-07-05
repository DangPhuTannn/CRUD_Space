package com.example.CRUDSpace.Model.DTO.Material;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialAddingForSpaceDTO {

    @NotNull(message = "FIELD_REQUIRED")
    UUID materialId;

    @NotBlank(message = "FIELD_REQUIRED")
    String materialName;

    int availableUnits;

    @NotNull(message = "FIELD_REQUIRED")
    UUID materialTypeId;

    @NotBlank(message = "FIELD_REQUIRED")
    String materialTypeName;

    @NotBlank(message = "FIELD_REQUIRED")
    String measurement;
}
