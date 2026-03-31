package com.tradehub.repository;

import com.tradehub.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByUserId(Long userId);

    Portfolio findByUserIdAndCoinName(Long userId, String coinName);
}