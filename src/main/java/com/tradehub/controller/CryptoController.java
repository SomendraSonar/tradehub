package com.tradehub.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    @GetMapping("/price")
    public Object getPrices() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,solana&vs_currencies=usd";

            return restTemplate.getForObject(url, Map.class);

        } catch (Exception e) {
            // fallback data if API fails
            return Map.of(
                "bitcoin", Map.of("usd", 65000),
                "ethereum", Map.of("usd", 3500),
                "solana", Map.of("usd", 150)
            );
        }
    }
}