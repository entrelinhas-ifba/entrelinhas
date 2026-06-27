package com.entrelinhas.api.controller;

import com.entrelinhas.api.service.TextCleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleaning")
@RequiredArgsConstructor
public class TextCleaningController {
    private final TextCleaningService service;

    @PostMapping
    public String limpar(
            @RequestBody String texto
    ) {
        return service.limpar(texto);
    }
}
