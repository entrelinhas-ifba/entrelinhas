package com.entrelinhas.api.service;

import com.entrelinhas.api.Dto.InterpretationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OllamaInterpretationService {

    private final OllamaClient ollamaClient;

    public String interpretar(String texto) {

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

        return resposta;
    }
}