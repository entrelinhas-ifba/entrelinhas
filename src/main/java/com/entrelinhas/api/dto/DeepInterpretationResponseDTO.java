package com.entrelinhas.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeepInterpretationResponseDTO {

    private String idiomaOriginal;
    private String traducao;
    private String resumo;
    private String temaPrincipal;
    private String intencaoAutor;
    private List<String> ideiasPrincipais;
    private String contextoHistorico;
    private List<String> referenciasHistoricas;
    private List<String> figurasDeLinguagem;
    private String analiseGeral;
    private String textoOriginal;
}