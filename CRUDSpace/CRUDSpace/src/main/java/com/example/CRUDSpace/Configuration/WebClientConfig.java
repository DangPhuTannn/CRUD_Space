package com.example.CRUDSpace.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(TuyaProperties props) {
        return WebClient.builder()
                .build();
    }
}
