package com.example.CRUDSpace.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@ConfigurationProperties(prefix = "tuya")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TuyaProperties {

    String clientId;
    String clientSecret;
    String region;

    public String baseUrl() {
        return "https://openapi.tuya" + region + ".com";
    }
}