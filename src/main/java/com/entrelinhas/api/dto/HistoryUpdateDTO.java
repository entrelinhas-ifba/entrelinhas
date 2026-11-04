
package com.entrelinhas.api.dto;

import jakarta.validation.constraints.NotBlank;

public record HistoryUpdateDTO(

        @NotBlank
        String textoOriginal,

        String idiomaOriginal,

        String resultado

) {}
