
package com.entrelinhas.api.service;


import com.entrelinhas.api.dto.*;
import com.entrelinhas.api.entity.History;
import com.entrelinhas.api.entity.User;
import com.entrelinhas.api.repository.HistoryRepository;
import com.entrelinhas.api.repository.UserRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public History salvarInterpretacao(
            String email,
            String textoOriginal,
            History.AnalysisType tipo,
            String idiomaOriginal,
            InterpretationResponseDTO resultado) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário autenticado não encontrado."
                        ));

        final String resultadoJson;

        resultadoJson = objectMapper.writeValueAsString(resultado);

        History history = new History();
        history.setUser(user);
        history.setTipoAnalise(tipo);
        history.setTextoOriginal(textoOriginal);
        history.setIdiomaOriginal(idiomaOriginal);
        history.setCriadoEm(LocalDateTime.now());
        history.setResultado(resultadoJson);

        return historyRepository.save(history);
    }


    @Transactional
    public History salvarInterpretacaoProfunda(
            String email,
            String textoOriginal,
            String idiomaOriginal,
            DeepInterpretationResponseDTO resultado
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário autenticado não encontrado."
                        )
                );

        final String resultadoJson;

        resultadoJson = objectMapper.writeValueAsString(resultado);

        History history = new History();
        history.setUser(user);
        history.setTipoAnalise(History.AnalysisType.PROFUNDA);
        history.setTextoOriginal(textoOriginal);
        history.setIdiomaOriginal(idiomaOriginal);
        history.setCriadoEm(LocalDateTime.now());
        history.setResultado(resultadoJson);

        return historyRepository.save(history);
    }

    @Transactional
    public HistoryResponseDTO criar(
            String email,
            HistoryRequestDTO dto
    ) {
        User user = buscarUsuario(email);

        History history = new History();

        history.setUser(user);
        history.setTipoAnalise(dto.tipoAnalise());
        history.setTextoOriginal(dto.textoOriginal());
        history.setIdiomaOriginal(dto.idiomaOriginal());
        history.setResultado(dto.resultado());
        history.setCriadoEm(LocalDateTime.now());

        History salvo = historyRepository.save(history);

        return converterParaDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<HistoryResponseDTO> listar(String email) {

        User user = buscarUsuario(email);

        return historyRepository
                .findByUserIdOrderByCriadoEmDesc(user.getId())
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<HistoryResponseDTO> listarPorTipo(
            String email,
            History.AnalysisType tipo
    ) {
        User user = buscarUsuario(email);

        return historyRepository
                .findByUserIdAndTipoAnaliseOrderByCriadoEmDesc(
                        user.getId(),
                        tipo
                )
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public HistoryResponseDTO buscarPorId(
            String email,
            Long id
    ) {
        User user = buscarUsuario(email);

        History history = buscarHistoricoDoUsuario(id, user.getId());

        return converterParaDTO(history);
    }

    @Transactional
    public HistoryResponseDTO atualizar(
            String email,
            Long id,
            HistoryUpdateDTO dto
    ) {
        User user = buscarUsuario(email);

        History history = buscarHistoricoDoUsuario(id, user.getId());

        history.setTextoOriginal(dto.textoOriginal());
        history.setIdiomaOriginal(dto.idiomaOriginal());
        history.setResultado(dto.resultado());

        History atualizado = historyRepository.save(history);

        return converterParaDTO(atualizado);
    }

    @Transactional
    public void excluir(String email, Long id) {

        User user = buscarUsuario(email);

        History history = buscarHistoricoDoUsuario(id, user.getId());

        historyRepository.delete(history);
    }


    private User buscarUsuario(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado."
                ));
    }

    private History buscarHistoricoDoUsuario(Long id, Long userId) {
        return historyRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Histórico não encontrado."
                ));
    }

    private HistoryResponseDTO converterParaDTO(History history) {
        return new HistoryResponseDTO(
                history.getId(),
                history.getTipoAnalise(),
                history.getTextoOriginal(),
                history.getIdiomaOriginal(),
                history.getCriadoEm(),
                history.getResultado()
        );
    }

}
