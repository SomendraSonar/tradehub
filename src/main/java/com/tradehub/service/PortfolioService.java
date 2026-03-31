package com.tradehub.service;

import com.tradehub.model.Portfolio;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private Portfolio portfolio = new Portfolio();

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void buy(String coin, double amount) {
        portfolio.buy(coin, amount);
    }

    public void sell(String coin, double amount) {
        portfolio.sell(coin, amount);
    }
}