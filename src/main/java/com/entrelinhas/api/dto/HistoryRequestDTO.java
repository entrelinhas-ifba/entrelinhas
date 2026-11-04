
package com.entrelinhas.api.dto;

import com.entrelinhas.api.entity.History;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HistoryRequestDTO(

        @NotNull
        History.AnalysisType tipoAnalise,

        @NotBlank
        String textoOriginal,

        String idiomaOriginal,

        String resultado

) {}
