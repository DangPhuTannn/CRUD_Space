package com.example.CRUDSpace.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.*;

import com.example.CRUDSpace.Configuration.TuyaProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TuyaUtils {

    TuyaProperties props;
    String EMPTY_BODY_SHA256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    WebClient webClient;
    ObjectMapper objectMapper = new ObjectMapper();

    public String hmacSHA256(String content, String secret) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] hash = sha256HMAC.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC SHA256 signature", e);
        }
    }

    public String getAccessToken() {

        long t = System.currentTimeMillis();
        String nonce = UUID.randomUUID().toString();
        String path = "/v1.0/token?grant_type=1";
        String stringToSign = "GET\n" + EMPTY_BODY_SHA256 + "\n\n" + path;
        String rawSign = props.getClientId() + t + nonce + stringToSign;
        String sign = hmacSHA256(rawSign, props.getClientSecret());

        return webClient.get()
                .uri(props.baseUrl() + path)
                .header("client_id", props.getClientId())
                .header("t", String.valueOf(t))
                .header("sign_method", "HMAC-SHA256")
                .header("nonce", nonce)
                .header("sign", sign)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("result").path("access_token").asText())
                .block();
    }

    public String callTuyaAPIs(String url, String method) {
        String token = getAccessToken();
        long t = System.currentTimeMillis();
        String nonce = UUID.randomUUID().toString();
        String path = "/v2.0/cloud/" + url;
        String stringToSign = method + "\n" + EMPTY_BODY_SHA256 + "\n\n" + path;
        String rawSign = props.getClientId() + token + t + nonce + stringToSign;
        String sign = hmacSHA256(rawSign, props.getClientSecret());

        WebClient.RequestHeadersSpec<?> requestSpec;

        WebClient.RequestHeadersUriSpec<?> uriSpec = switch (method.toUpperCase()) {
            case "GET" -> webClient.get();
            case "POST" -> webClient.post();
            case "DELETE" -> webClient.delete();
            case "PUT" -> webClient.put();
            default -> throw new IllegalArgumentException("Unsupported method: " + method);
        };

        requestSpec = uriSpec
                .uri(props.baseUrl() + path)
                .header("client_id", props.getClientId())
                .header("access_token", token)
                .header("sign_method", "HMAC-SHA256")
                .header("t", String.valueOf(t))
                .header("nonce", nonce)
                .header("sign", sign)
                .header("mode", "cors")
                .accept(MediaType.APPLICATION_JSON);

        return requestSpec
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public <T> T parseTuyaResult(String response, Class<T> clazz) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response);
        JsonNode resultNode = root.get("result");
        return objectMapper.treeToValue(resultNode, clazz);
    }

    public <T> List<T> parseTuyaResultAsList(String response, String[] fields, Class<T> clazz) throws IOException {
        JsonNode root = objectMapper.readTree(response);
        JsonNode resultNode = root.get("result");
        for (String nextField : fields) {
            resultNode = resultNode.get(nextField);
        }
        return objectMapper.readerForListOf(clazz).readValue(resultNode);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

}