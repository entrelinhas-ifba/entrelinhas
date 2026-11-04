
package com.entrelinhas.api.dto;

import com.entrelinhas.api.entity.History;

import java.time.LocalDateTime;

public record HistoryResponseDTO(

        Long id,
        History.AnalysisType tipoAnalise,
        String textoOriginal,
        String idiomaOriginal,
        LocalDateTime criadoEm,
        String resultado

) {}
