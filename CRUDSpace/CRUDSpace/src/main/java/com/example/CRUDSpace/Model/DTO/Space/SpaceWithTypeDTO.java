package com.example.CRUDSpace.Model.DTO.Space;

import java.util.UUID;

import com.example.CRUDSpace.Model.DTO.Type.TypeDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpaceWithTypeDTO {

    UUID spaceId;

    String spaceName;

    UUID parentId;

    TypeDTO type;
}
