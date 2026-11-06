
package com.entrelinhas.api.controller;

import com.entrelinhas.api.dto.HistoryRequestDTO;
import com.entrelinhas.api.dto.HistoryResponseDTO;
import com.entrelinhas.api.dto.HistoryStatsResponseDTO;
import com.entrelinhas.api.dto.HistoryUpdateDTO;
import com.entrelinhas.api.entity.History;
import com.entrelinhas.api.service.HistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historicos")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping
    public ResponseEntity<HistoryResponseDTO> criar(
            @Valid @RequestBody HistoryRequestDTO dto,
            Authentication authentication
    ) {
        HistoryResponseDTO response = historyService.criar(
                authentication.getName(),
                dto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<HistoryResponseDTO>> listar(
            @RequestParam(required = false) History.AnalysisType tipo,
             Authentication authentication
    ) {
        String email = authentication.getName();

        List<HistoryResponseDTO> historicos;

        if (tipo != null) {
            historicos = historyService.listarPorTipo(email, tipo);
        } else {
            historicos = historyService.listar(email);
        }

        return ResponseEntity.ok(historicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryResponseDTO> buscarPorId(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                historyService.buscarPorId(authentication.getName(), id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoryResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody HistoryUpdateDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                historyService.atualizar(
                        authentication.getName(),
                        id,
                        dto
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            Authentication authentication
    ) {
        historyService.excluir(authentication.getName(), id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<HistoryStatsResponseDTO> obterEstatisticas(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                historyService.obterEstatisticas(authentication.getName())
        );
    }
}
