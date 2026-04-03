package com.tradehub.service;

import com.tradehub.model.Portfolio;
import com.tradehub.repository.PortfolioRepository;
import com.tradehub.config.PriceWebSocketHandler;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class TradeService {

    private final PortfolioRepository repo;

    public TradeService(PortfolioRepository repo) {
        this.repo = repo;
    }

    // 🔥 CACHE VARIABLES (IMPORTANT)
    private Map<String, Double> cachedPrices = new HashMap<>();
    private long lastFetchTime = 0;

    // 🔥 LIVE PRICE (CACHE + API + WEBSOCKET)
    public Map<String, Double> getPrices() {

        long now = System.currentTimeMillis();

        // ✅ CACHE: avoid hitting API too frequently
        if (now - lastFetchTime < 10000 && !cachedPrices.isEmpty()) {
            return cachedPrices;
        }

        Map<String, Double> prices = new HashMap<>();

        try {
            System.out.println("🚀 CALLING API...");

            String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,solana&vs_currencies=usd";

            java.net.URL apiUrl = new java.net.URL(url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) apiUrl.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("HTTP RESPONSE CODE: " + responseCode);

            if (responseCode != 200) {
                throw new RuntimeException("Rate limited");
            }

            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String json = response.toString();

            prices.put("bitcoin", Double.parseDouble(json.split("\"bitcoin\":\\{\"usd\":")[1].split("}")[0]));
            prices.put("ethereum", Double.parseDouble(json.split("\"ethereum\":\\{\"usd\":")[1].split("}")[0]));
            prices.put("solana", Double.parseDouble(json.split("\"solana\":\\{\"usd\":")[1].split("}")[0]));

            // ✅ UPDATE CACHE
            cachedPrices = prices;
            lastFetchTime = now;

        } catch (Exception e) {
            System.out.println("⚠️ USING CACHED DATA");

            if (!cachedPrices.isEmpty()) {
                return cachedPrices;
            }

            prices.put("bitcoin", 67000.0);
            prices.put("ethereum", 2000.0);
            prices.put("solana", 80.0);
        }

        // 🔥 WEBSOCKET BROADCAST
        try {
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(prices);

            PriceWebSocketHandler.broadcastPrices(message);

        } catch (Exception e) {
            System.out.println("⚠️ WebSocket failed");
        }

        return prices;
    }

    // 💰 BUY
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

    // 📉 SELL
    public String sell(Long userId, String coin, double amount) {

        Portfolio existing = repo.findByUserIdAndCoinName(userId, coin);

        if (existing == null || existing.getQuantity() < amount) {
            return "Not enough balance!";
        }

        existing.setQuantity(existing.getQuantity() - amount);
        repo.save(existing);

        return "Sold " + amount + " of " + coin;
    }

    // 📊 PORTFOLIO
    public List<Portfolio> getPortfolio(Long userId) {
        return repo.findByUserId(userId);
    }
}