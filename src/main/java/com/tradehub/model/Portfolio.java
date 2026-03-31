package com.tradehub.model;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    private Map<String, Double> holdings = new HashMap<>();

    public Map<String, Double> getHoldings() {
        return holdings;
    }

    public void buy(String coin, double amount) {
        holdings.put(coin, holdings.getOrDefault(coin, 0.0) + amount);
    }

    public void sell(String coin, double amount) {
        holdings.put(coin, holdings.getOrDefault(coin, 0.0) - amount);
    }
}