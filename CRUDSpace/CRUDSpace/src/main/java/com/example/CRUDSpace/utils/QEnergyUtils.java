package com.example.CRUDSpace.utils;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.CRUDSpace.Configuration.QenergyProperties;
import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QEnergyUtils {

    QenergyProperties props;
    WebClient webClient;

    public void getAccessToken() {

        Map<String, Object> response = webClient.post()
                .uri(props.getHost() + props.getLoginEndpoint())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "role", props.getRole(),
                        "password", props.getPassword(),
                        "email", props.getEmail()))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        if (response == null || !response.containsKey("access_token")) {
            throw new AppException(ErrorCode.FAILED_TO_GET_ACCESS_TOKEN);
        }
        props.setAccessToken((String) response.get("access_token"));

    }

    public BigDecimal getCurrentEnergyConsumptionBySiteId(String siteId) {
        if (props.getAccessToken().isEmpty()) {
            getAccessToken();
        }

        Map<String, Object> response = webClient.get()
                .uri(props.getHost() + props.getSiteEndpoint() + siteId + "/")
                .header("Authorization", "Bearer " + props.getAccessToken())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null || !response.containsKey("live_power")) {
            throw new AppException(ErrorCode.FAILED_TO_GET_LIVE_POWER);
        }

        return new BigDecimal(response.get("live_power").toString());

    }

}
