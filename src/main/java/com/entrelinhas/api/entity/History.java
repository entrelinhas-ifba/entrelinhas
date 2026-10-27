package com.entrelinhas.api.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "histories")
@Data
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_analise", nullable = false)
    private AnalysisType tipoAnalise;

    @Column(name = "texto_original", columnDefinition = "TEXT", nullable = false)
    private String textoOriginal;

    @Column(name = "idioma_original")
    private String idiomaOriginal;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "resultado", columnDefinition = "TEXT")
    private String resultado;


    public enum AnalysisType {

        SIMPLES,
        PROFUNDA
    }
}