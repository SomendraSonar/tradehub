package com.tradehub.config;

import com.tradehub.service.TradeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceScheduler {

    private final TradeService service;

    public PriceScheduler(TradeService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 5000) // every 5 sec
    public void sendPrices() {
        service.getPrices(); // this triggers broadcast
    }
}