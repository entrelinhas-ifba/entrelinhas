package com.entrelinhas.api.service;

import com.entrelinhas.api.Dto.OllamaRequest;
import com.entrelinhas.api.Dto.OllamaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OllamaClient {

    @Value("${ollama.url}")
    private String url;

    @Value("${ollama.model}")
    private String model;

    private final RestClient restClient;

    public OllamaClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String generate(String prompt) {

        System.out.println("Enviando prompt para o Ollama...");

        OllamaRequest request =
                new OllamaRequest(model, prompt, false);

        OllamaResponse response =
                restClient.post()
                        .uri(url)
                        .body(request)
                        .retrieve()
                        .body(OllamaResponse.class);

        System.out.println("Resposta recebida.");

        return response.getResponse();
    }

}