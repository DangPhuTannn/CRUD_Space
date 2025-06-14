package com.example.CRUDSpace.Model.DTO.Space;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpaceDTO {

    UUID spaceId;

    String spaceName;

    UUID parentId;

}
