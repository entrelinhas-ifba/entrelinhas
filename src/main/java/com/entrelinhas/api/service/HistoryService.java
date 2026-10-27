
package com.entrelinhas.api.service;


import com.entrelinhas.api.dto.DeepInterpretationResponseDTO;
import com.entrelinhas.api.dto.InterpretationResponseDTO;
import com.entrelinhas.api.entity.History;
import com.entrelinhas.api.entity.User;
import com.entrelinhas.api.repository.HistoryRepository;
import com.entrelinhas.api.repository.UserRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;

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

}
