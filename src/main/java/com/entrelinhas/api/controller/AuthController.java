package com.entrelinhas.api.controller;

import com.entrelinhas.api.dto.LoginRequestDTO;
import com.entrelinhas.api.dto.RegisterRequestDTO;
import com.entrelinhas.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> cadastrar(
            @Valid @RequestBody RegisterRequestDTO request) {
        try {
            authService.cadastrar(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "mensagem",
                            "Usuário cadastrado com sucesso."
                    ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequestDTO request) {
        try {
            return ResponseEntity.ok(
                    authService.login(request)
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "erro",
                            "E-mail ou senha inválidos."
                    ));
        }
    }
}