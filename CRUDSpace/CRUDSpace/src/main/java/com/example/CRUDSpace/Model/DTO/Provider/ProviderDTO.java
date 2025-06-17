package com.example.CRUDSpace.Model.DTO.Provider;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderDTO {

    UUID providerId;

    String providerName;
}
