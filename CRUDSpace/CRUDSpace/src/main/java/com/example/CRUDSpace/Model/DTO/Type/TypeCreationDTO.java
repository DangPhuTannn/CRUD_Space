package com.example.CRUDSpace.Model.DTO.Type;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeCreationDTO {

    @Column(nullable = false)
    int level;

    @Column(nullable = false)
    String typeName;
}
