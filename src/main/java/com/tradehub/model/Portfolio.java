package com.tradehub.model;

import jakarta.persistence.*;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coinName;
    private double quantity;

    private Long userId;   // 🔥 REQUIRED (YOU MISSED THIS)

    public Portfolio() {}

    public Portfolio(String coinName, double quantity, Long userId) {
        this.coinName = coinName;
        this.quantity = quantity;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getCoinName() {
        return coinName;
    }

    public double getQuantity() {
        return quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}