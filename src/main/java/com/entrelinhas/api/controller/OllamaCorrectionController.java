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

    @PostMapping
    public String corrigir(
            @RequestBody String texto
    ){

        return service.corrigir(texto);

    }

}