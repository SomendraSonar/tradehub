package com.tradehub.controller;

import com.tradehub.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trade")
@CrossOrigin(origins = "*")
public class TradeController {

    private final PortfolioService service;

    public TradeController(PortfolioService service) {
        this.service = service;
    }

    @PostMapping("/buy")
    public String buy(@RequestParam String coin, @RequestParam double amount) {
        service.buy(coin, amount);
        return "Bought " + amount + " of " + coin;
    }

    @PostMapping("/sell")
    public String sell(@RequestParam String coin, @RequestParam double amount) {
        service.sell(coin, amount);
        return "Sold " + amount + " of " + coin;
    }

    @GetMapping("/portfolio")
    public Object getPortfolio() {
        return service.getPortfolio().getHoldings();
    }
}