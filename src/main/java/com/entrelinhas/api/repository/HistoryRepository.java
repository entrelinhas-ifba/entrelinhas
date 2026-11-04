package com.entrelinhas.api.repository;

import com.entrelinhas.api.entity.History;
import com.entrelinhas.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByUserIdOrderByCriadoEmDesc(Long userId);

    List<History> findByUserIdAndTipoAnaliseOrderByCriadoEmDesc(
            Long userId,
            History.AnalysisType tipoAnalise
    );

    Optional<History> findByIdAndUserId(Long id, Long userId);
}
