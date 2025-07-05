package com.example.CRUDSpace.Model.DTO.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Material.MaterialAddingForSpaceDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialsInSpaceDTO {

    @NotNull(message = "FIELD_REQUIRED")
    UUID spaceId;

    @NotBlank(message = "FIELD_REQUIRED")
    String spaceName;

    @Builder.Default
    List<MaterialAddingForSpaceDTO> materials = new ArrayList<>();
}
