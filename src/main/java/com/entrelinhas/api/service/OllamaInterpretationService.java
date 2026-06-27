package com.entrelinhas.api.service;

import com.entrelinhas.api.Dto.InterpretationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OllamaInterpretationService {

    public InterpretationResponseDTO interpretar(
            String texto
    ) {

        return InterpretationResponseDTO
                .builder()
                .tema("Tema de teste")
                .resumo("Resumo de teste")
                .palavrasChave(
                        List.of(
                                "ocr",
                                "texto",
                                "livro"
                        )
                )
                .build();
    }
}