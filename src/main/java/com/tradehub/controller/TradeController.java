package com.tradehub.controller;

import com.tradehub.model.Portfolio;
import com.tradehub.service.TradeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    private final TradeService service;

    public TradeController(TradeService service) {
        this.service = service;
    }

    @PostMapping("/buy")
    public Portfolio buy(@RequestParam Long userId,
                         @RequestParam String coin,
                         @RequestParam double qty) {
        return service.buy(userId, coin, qty);
    }

    @PostMapping("/sell")
    public Portfolio sell(@RequestParam Long userId,
                          @RequestParam String coin,
                          @RequestParam double qty) {
        return service.sell(userId, coin, qty);
    }

    @GetMapping("/value")
    public double value(@RequestParam Long userId) {
        return service.getPortfolioValue(userId);
    }

    @GetMapping("/investment")
    public double investment(@RequestParam Long userId) {
        return service.getTotalInvestment(userId);
    }

    @GetMapping("/profit")
    public double profit(@RequestParam Long userId) {
        return service.getProfitOrLoss(userId);
    }
}