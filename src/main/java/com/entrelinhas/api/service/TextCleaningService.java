package com.entrelinhas.api.service;

import org.springframework.stereotype.Service;

@Service
public class TextCleaningService {

    public String limparTexto(String texto) {

        return texto
                .replaceAll("[^\\p{L}\\p{N}\\p{P}\\s]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public String limpar(String texto) {

        texto = texto.replace("\r", "");

        texto = texto.replaceAll("-\\s*\\n\\s*", "");

        texto = texto.replaceAll("[ ]{2,}", " ");

        texto = texto.replaceAll("\\n{3,}", "\n\n");

        return texto.trim();
    }
}