package com.entrelinhas.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OllamaCorrectionService {

    private final OllamaClient ollamaClient;

    public String corrigir(String texto) {

        String prompt = """
Você é especialista em recuperação de textos obtidos por OCR.

O texto abaixo foi extraído de uma fotografia de um livro e contém diversos erros de OCR.

Sua tarefa é reconstruir o texto original.

Regras:

- corrija palavras quebradas;
- corrija letras trocadas;
- remova caracteres aleatórios;
- corrija acentos;
- una palavras separadas incorretamente;
- mantenha os parágrafos;
- NÃO faça resumo;
- NÃO explique nada;
- NÃO acrescente comentários;
- NÃO altere o sentido do texto;
- quando houver alta confiança, reescreva a frase corretamente;
- preserve exatamente o conteúdo do livro.

Retorne apenas o texto corrigido.

Texto OCR:

%s
"""
                .formatted(texto);

        return ollamaClient.generate(prompt);

    }

}