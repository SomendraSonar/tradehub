package com.tradehub.controller;

import com.tradehub.model.Portfolio;
import com.tradehub.service.TradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class TradeController {

    private final TradeService service;

    public TradeController(TradeService service) {
        this.service = service;
    }

    // GET prices
    @GetMapping("/prices")
    public Map<String, Object> getPrices() {
        return service.getPrices();
    }

    // BUY
    @PostMapping("/trade/buy")
    public String buy(@RequestBody java.util.Map<String, Object> body) {

        Long userId = Long.parseLong(body.get("userId").toString());
        String coin = (String) body.get("coin");
        double amount = Double.parseDouble(body.get("amount").toString());

        return service.buy(userId, coin, amount);
    }

    // SELL
    @PostMapping("/trade/sell")
    public String sell(@RequestBody java.util.Map<String, Object> body) {

        Long userId = Long.parseLong(body.get("userId").toString());
        String coin = (String) body.get("coin");
        double amount = Double.parseDouble(body.get("amount").toString());

        return service.sell(userId, coin, amount);
    }

    // Portfolio
    @GetMapping("/portfolio/{userId}")
    public List<Portfolio> getPortfolio(@PathVariable Long userId) {
        return service.getPortfolio(userId);
    }
}