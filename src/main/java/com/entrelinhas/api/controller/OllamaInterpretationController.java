package com.entrelinhas.api.controller;

import com.entrelinhas.api.Dto.InterpretationResponseDTO;
import com.entrelinhas.api.service.OllamaInterpretationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interpretation")
@RequiredArgsConstructor
public class OllamaInterpretationController {

    private final OllamaInterpretationService service;

    @PostMapping
    public InterpretationResponseDTO interpretar(
            @RequestBody String request
    ) {

        return service.interpretar(
                request
        );
    }
}