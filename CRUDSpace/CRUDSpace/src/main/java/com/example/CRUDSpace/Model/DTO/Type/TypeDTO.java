package com.example.CRUDSpace.Model.DTO.Type;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeDTO {

    UUID typeId;

    int level;

    String typeName;
}
