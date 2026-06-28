package com.entrelinhas.api.controller;

import com.entrelinhas.api.Dto.InterpretationDTO;
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
    public String interpretar(@RequestBody String texto) {

        System.out.println("Controller iniciado");

        String resposta = service.interpretar(texto);

        System.out.println("Controller finalizado");

        return resposta;
    }

}