package com.entrelinhas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterpretationResponseDTO {

    private String resumo;

    private String tema;

    private String intencaoAutor;

    private List<String> ideiasPrincipais;

    private String textoOriginal;

}