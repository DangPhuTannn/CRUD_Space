package com.example.CRUDSpace.Model.DTO.Value;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValueDTO {

    UUID valueId;

    String valueName;
}
