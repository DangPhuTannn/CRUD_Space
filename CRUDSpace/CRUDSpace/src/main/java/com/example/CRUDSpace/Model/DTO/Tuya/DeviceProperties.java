package com.example.CRUDSpace.Model.DTO.Tuya;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceProperties {
    String code;
    String custom_name;
    int dp_id;
    String time;
    String type;
    String value;
}
