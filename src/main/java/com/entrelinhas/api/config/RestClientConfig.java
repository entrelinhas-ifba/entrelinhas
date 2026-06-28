package com.entrelinhas.api.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {

        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        // Tempo para conectar
        factory.setConnectTimeout(260000);

        // Tempo máximo aguardando resposta (2 minutos)
        factory.setReadTimeout(400000);

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }
}