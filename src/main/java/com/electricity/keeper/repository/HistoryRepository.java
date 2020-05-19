package com.electricity.keeper.repository;

import com.electricity.keeper.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
    void deleteByAccountId(long accountId);
}
