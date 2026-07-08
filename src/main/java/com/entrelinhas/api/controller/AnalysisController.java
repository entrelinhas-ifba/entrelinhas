package com.entrelinhas.api.controller;

import com.entrelinhas.api.Dto.InterpretationResponseDTO;
import com.entrelinhas.api.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<InterpretationResponseDTO> analisar(
            @RequestParam MultipartFile file
    ) {

        try {

            return ResponseEntity.ok(
                    analysisService.analisar(file)
            );

        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();

        }

    }

}