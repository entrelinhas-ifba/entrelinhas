package com.entrelinhas.api.controller;

import com.entrelinhas.api.dto.DeepInterpretationResponseDTO;
import com.entrelinhas.api.dto.InterpretationResponseDTO;
import com.entrelinhas.api.entity.History;
import com.entrelinhas.api.service.AnalysisService;
import com.entrelinhas.api.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private HistoryService historyService;


    @PostMapping
    public ResponseEntity<InterpretationResponseDTO> analisar(
            @RequestParam MultipartFile file, Authentication authentication
    ) throws Exception {

        InterpretationResponseDTO resposta = analysisService.interpretar(file);

        historyService.salvarInterpretacao(
                authentication.getName(),
                resposta.getTextoOriginal(),
                History.AnalysisType.SIMPLES,
                null,
                resposta
        );

        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping("/profunda")
    public ResponseEntity<DeepInterpretationResponseDTO>
    analisarProfundamente(
            @RequestParam MultipartFile file,
            Authentication authentication
    ) {
        try {
            DeepInterpretationResponseDTO resposta =
                    analysisService.analisarProfundamente(file);

            historyService.salvarInterpretacaoProfunda(
                    authentication.getName(),
                    resposta.getTextoOriginal(),
                    resposta.getIdiomaOriginal(),
                    resposta
            );

            return ResponseEntity.ok(resposta);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}