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
public class MaterialCreationDTO {

    @NotBlank(message = "FIELD_REQUIRED")
    String materialName;

    @NotNull(message = "FIELD_REQUIRED")
    UUID materialTypeId;

}
