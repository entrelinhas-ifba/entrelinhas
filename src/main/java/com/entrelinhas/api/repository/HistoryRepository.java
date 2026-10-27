package com.entrelinhas.api.repository;

import com.entrelinhas.api.entity.History;
import com.entrelinhas.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByUserOrderByCriadoEmDesc(User user);

}
