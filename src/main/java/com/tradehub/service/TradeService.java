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
    // 🔥 SAFE LIVE + FALLBACK PRICES
    public Map<String, Object> getPrices() {

        String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,solana&vs_currencies=usd";

        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return response;

        } catch (Exception e) {

            // 🔥 Fallback if API fails (VERY IMPORTANT)
            Map<String, Object> fallback = new HashMap<>();

            Map<String, Double> btc = new HashMap<>();
            btc.put("usd", 67000.0);

            Map<String, Double> eth = new HashMap<>();
            eth.put("usd", 2000.0);

            Map<String, Double> sol = new HashMap<>();
            sol.put("usd", 80.0);

            fallback.put("bitcoin", btc);
            fallback.put("ethereum", eth);
            fallback.put("solana", sol);

            System.out.println("⚠️ CoinGecko API failed, using fallback data");

            return fallback;
        }
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