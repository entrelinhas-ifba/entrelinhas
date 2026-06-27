package com.entrelinhas.api.service;

import org.springframework.stereotype.Service;

@Service
public class OllamaCorrectionService {

    public String corrigir(String texto) {

        return """
                [CORREÇÃO OCR]

                %s
                """
                .formatted(texto);
    }
}