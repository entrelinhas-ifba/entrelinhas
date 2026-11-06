package com.entrelinhas.api.dto;

public record HistoryStatsResponseDTO(
        long paginasEsteMes,
        long palavrasAnalisadas
) {}