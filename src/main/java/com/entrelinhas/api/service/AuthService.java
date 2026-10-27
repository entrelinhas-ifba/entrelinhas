package com.entrelinhas.api.service;


import com.entrelinhas.api.dto.LoginRequestDTO;
import com.entrelinhas.api.dto.LoginResponseDTO;
import com.entrelinhas.api.dto.RegisterRequestDTO;
import com.entrelinhas.api.entity.User;
import com.entrelinhas.api.repository.UserRepository;
import com.entrelinhas.api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public void cadastrar(RegisterRequestDTO request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado."
            );
        }

        User user = new User();
        user.setNome(request.getNome().trim());
        user.setEmail(email);
        user.setSenha(passwordEncoder.encode(request.getSenha()));
        user.setCriadoEm(LocalDateTime.now());

        userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        String email = request.getEmail().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.getSenha()
                )
        );

        String token = jwtService.gerarToken(email);

        return new LoginResponseDTO(
                token,
                "Bearer",
                86400000L
        );
    }
}