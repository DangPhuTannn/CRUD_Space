package com.example.CRUDSpace.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@ConfigurationProperties(prefix = "qenergy")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QenergyProperties {

    String role;
    String password;
    String email;
    String loginEndpoint;
    String host;
    String siteEndpoint;
    String accessToken;

}
