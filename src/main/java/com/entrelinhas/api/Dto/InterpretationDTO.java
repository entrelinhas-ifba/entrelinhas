package com.entrelinhas.api.Dto;

import lombok.Data;

import java.util.List;

@Data
public class InterpretationDTO {

    private String resumo;

    private String contextoHistorico;

    private String intencao;

    private List<String> palavrasChave;


}