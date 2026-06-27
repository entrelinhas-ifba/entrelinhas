package com.entrelinhas.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterpretationResponseDTO {

    private String tema;

    private String resumo;

    private List<String> palavrasChave;

}