package com.entrelinhas.api.service;

import com.entrelinhas.api.dto.DeepInterpretationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Service
public class DeepInterpretationService {

    private final OllamaClient ollamaClient;
    private final ResourceLoader resourceLoader;

    public DeepInterpretationService(
            OllamaClient ollamaClient, ResourceLoader resourceLoader
    ) {
        this.ollamaClient = ollamaClient;
        this.resourceLoader = resourceLoader;
    }


    public DeepInterpretationResponseDTO interpretar(
            String texto,
            String idioma
    ) {
        String prompt = carregarPromptProfundo()
                .replace("{{idioma}}", idioma == null ? "" : idioma)
                .replace("{{texto}}", texto == null ? "" : texto);

        String resposta = ollamaClient.generate(prompt);
        DeepInterpretationResponseDTO resultado = converterResposta(resposta);

        resultado.setTextoOriginal(texto);

        return resultado;
    }

    private String carregarPromptProfundo() {
        Resource resource = resourceLoader.getResource(
                "classpath:prompts/interpretacao-profunda.txt"
        );

        try (var inputStream = resource.getInputStream()) {
            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Não foi possível carregar o prompt de interpretação profunda.",
                    e
            );
        }
    }

    private DeepInterpretationResponseDTO converterResposta(
            String resposta
    ) {

        String idioma =
                extrairCampo(
                        resposta,
                        "Idioma original:",
                        "Tradução:"
                );

        String traducao =
                extrairCampo(
                        resposta,
                        "Tradução:",
                        "Resumo:"
                );

        String resumo =
                extrairCampo(
                        resposta,
                        "Resumo:",
                        "Tema principal:"
                );

        String tema =
                extrairCampo(
                        resposta,
                        "Tema principal:",
                        "Intenção do autor:"
                );

        String intencao =
                extrairCampo(
                        resposta,
                        "Intenção do autor:",
                        "Ideias principais:"
                );

        String ideiasTexto =
                extrairCampo(
                        resposta,
                        "Ideias principais:",
                        "Contexto histórico:"
                );

        String contexto =
                extrairCampo(
                        resposta,
                        "Contexto histórico:",
                        "Referências históricas:"
                );

        String referenciasTexto =
                extrairCampo(
                        resposta,
                        "Referências históricas:",
                        "Figuras de linguagem:"
                );

        String figurasTexto =
                extrairCampo(
                        resposta,
                        "Figuras de linguagem:",
                        "Análise geral:"
                );

        String analise =
                extrairCampo(
                        resposta,
                        "Análise geral:",
                        null
                );

        return DeepInterpretationResponseDTO.builder()
                .idiomaOriginal(idioma)
                .traducao(traducao)
                .resumo(resumo)
                .temaPrincipal(tema)
                .intencaoAutor(intencao)
                .ideiasPrincipais(extrairLista(ideiasTexto))
                .contextoHistorico(contexto)
                .referenciasHistoricas(extrairLista(referenciasTexto))
                .figurasDeLinguagem(extrairLista(figurasTexto))
                .analiseGeral(analise)
                .build();
    }

    private List<String> extrairLista(String texto) {

        return Arrays.stream(texto.split("\n"))
                .map(String::trim)
                .filter(linha -> linha.startsWith("-"))
                .map(linha -> linha.substring(1).trim())
                .filter(linha -> !linha.isBlank())
                .toList();
    }

    private String extrairCampo(
            String texto,
            String inicio,
            String fim
    ) {

        int i = texto.indexOf(inicio);

        if (i == -1) {
            return "";
        }

        i += inicio.length();

        int f;

        if (fim == null) {
            f = texto.length();
        } else {
            f = texto.indexOf(fim, i);

            if (f == -1) {
                f = texto.length();
            }
        }

        return texto.substring(i, f).trim();
    }
}