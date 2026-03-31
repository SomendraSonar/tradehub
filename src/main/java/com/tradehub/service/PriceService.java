package com.tradehub.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class PriceService {

    private final RestTemplate restTemplate = new RestTemplate();

    public double getPrice(String coin) {

        try {
            coin = coin.toLowerCase();

            String url = "https://api.coingecko.com/api/v3/simple/price?ids="
                    + coin + "&vs_currencies=usd";

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            Map<String, Object> coinData = (Map<String, Object>) response.get(coin);

            return Double.parseDouble(coinData.get("usd").toString());

        } catch (Exception e) {
            System.out.println("Error fetching price: " + e.getMessage());
            return 0.0;
        }
    }
}