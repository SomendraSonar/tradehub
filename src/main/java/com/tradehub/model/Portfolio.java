package com.tradehub.model;

import jakarta.persistence.*;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coinName;
    private double quantity;
    private Long userId;

    // 🔥 BUY
    public void buy(String coin, double amount) {
        if (this.coinName == null) {
            this.coinName = coin;
        }

        if (!this.coinName.equalsIgnoreCase(coin)) {
            throw new RuntimeException("Different coin in this portfolio");
        }

        this.quantity += amount;
    }

    // 🔥 SELL
    public void sell(String coin, double amount) {
        if (!this.coinName.equalsIgnoreCase(coin)) {
            throw new RuntimeException("Coin mismatch");
        }

        if (this.quantity < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        this.quantity -= amount;
    }

    // GETTERS & SETTERS
    public Long getId() { return id; }

    public String getCoinName() { return coinName; }
    public void setCoinName(String coinName) { this.coinName = coinName; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}