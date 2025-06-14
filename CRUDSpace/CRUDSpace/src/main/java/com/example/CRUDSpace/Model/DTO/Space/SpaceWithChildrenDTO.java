package com.example.CRUDSpace.Model.DTO.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpaceWithChildrenDTO {

    UUID spaceId;

    String spaceName;

    @Builder.Default
    List<SpaceWithChildrenDTO> childrenSpaces = new ArrayList<>();
}
