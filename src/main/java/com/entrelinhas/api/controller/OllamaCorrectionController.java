package com.entrelinhas.api.controller;

import com.entrelinhas.api.service.OllamaCorrectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/correction")
@RequiredArgsConstructor
public class OllamaCorrectionController {

    private final OllamaCorrectionService service;

    //private final OllamaService ollamaService;

    /*public String corrigir(String texto) {

        String prompt = """
                Você recebeu um texto extraído por OCR.

                Sua tarefa é corrigir apenas erros causados pelo OCR.

                Regras:
                - Não resuma.
                - Não interprete.
                - Não adicione informações.
                - Não remova conteúdo.
                - Preserve a estrutura do texto.
                - Corrija apenas palavras reconhecidas incorretamente.

                Retorne apenas o texto corrigido.

                Texto:
                %s
                """
                .formatted(texto);

        return ollamaService.generate(prompt);
    }*/
}