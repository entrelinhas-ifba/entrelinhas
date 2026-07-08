package com.entrelinhas.api.service;

import com.entrelinhas.api.Dto.InterpretationDTO;
import com.entrelinhas.api.Dto.InterpretationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OllamaInterpretationService {

    private final OllamaClient ollamaClient;

    public InterpretationResponseDTO interpretar(String texto) {

        String prompt = """
                                Você é um assistente especializado em interpretação de textos.
                
                                                                         Sua tarefa é interpretar SOMENTE o conteúdo que estiver entre as marcações
                                                                         <TEXTO> e </TEXTO>.
                
                                                                         As instruções acima NÃO fazem parte do texto.
                
                                                                         Regras:
                
                                                                         - Não utilize conhecimentos externos.
                                                                         - Caso existam palavras corrompidas pelo OCR, deduza o significado pelo contexto.
                                                                         - Ignore sequências sem sentido provenientes do OCR.
                                                                         - Não mencione erros de OCR na resposta.
                                                                         - Caso alguma informação não possa ser determinada pelo texto, responda "Não é possível determinar".
                
                                                                         Responda exatamente neste formato:
                
                                                                         Resumo:
                                                                         (no máximo 3 linhas)
                
                                                                         Tema principal:
                                                                         (uma frase)
                
                                                                         Intenção do autor:
                                                                         (uma frase)
                
                                                                         Ideias principais:
                                                                         - ...
                                                                         - ...
                                                                         - ...
                
                                                                         <TEXTO>
                                                                         %s
                                                                         </TEXTO>
                """.formatted(texto);

        String resposta = ollamaClient.generate(prompt);

        System.out.println("Retornando resposta para o controller");

        return converterResposta(resposta);
    }

    private InterpretationResponseDTO converterResposta(String resposta) {

        String resumo = extrairCampo(resposta, "Resumo:", "Tema principal:");
        String tema = extrairCampo(resposta, "Tema principal:", "Intenção do autor:");
        String intencao = extrairCampo(resposta, "Intenção do autor:", "Ideias principais:");

        List<String> ideias = Arrays.stream(
                        resposta.substring(resposta.indexOf("Ideias principais:"))
                                .replace("Ideias principais:", "")
                                .split("\n"))
                .map(String::trim)
                .filter(l -> l.startsWith("-"))
                .map(l -> l.substring(1).trim())
                .toList();

        return InterpretationResponseDTO.builder()
                .resumo(resumo)
                .tema(tema)
                .intencaoAutor(intencao)
                .ideiasPrincipais(ideias)
                .build();
    }

    private String extrairCampo(String texto, String inicio, String fim) {

        int i = texto.indexOf(inicio);

        if (i == -1)
            return "";

        i += inicio.length();

        int f = texto.indexOf(fim);

        if (f == -1)
            f = texto.length();

        return texto.substring(i, f).trim();
    }
}