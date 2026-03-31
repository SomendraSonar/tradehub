package com.tradehub.service;

import com.tradehub.model.Portfolio;
import com.tradehub.repository.PortfolioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TradeService {

    private final PortfolioRepository repo;

    public TradeService(PortfolioRepository repo) {
        this.repo = repo;
    }

    // 🔥 REAL LIVE CRYPTO PRICES (CoinGecko API)
    public Map<String, Object> getPrices() {

        String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,solana&vs_currencies=usd";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        return response;
    }

    // 🔥 BUY
    public String buy(Long userId, String coin, double amount) {

        Portfolio existing = repo.findByUserIdAndCoinName(userId, coin);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + amount);
            repo.save(existing);
        } else {
            Portfolio newCoin = new Portfolio(coin, amount, userId);
            repo.save(newCoin);
        }

        return "Bought " + amount + " of " + coin;
    }

    // 🔥 SELL
    public String sell(Long userId, String coin, double amount) {

        Portfolio existing = repo.findByUserIdAndCoinName(userId, coin);

        if (existing == null || existing.getQuantity() < amount) {
            return "Not enough balance!";
        }

        existing.setQuantity(existing.getQuantity() - amount);
        repo.save(existing);

        return "Sold " + amount + " of " + coin;
    }

    // 🔥 PORTFOLIO
    public List<Portfolio> getPortfolio(Long userId) {
        return repo.findByUserId(userId);
    }
}