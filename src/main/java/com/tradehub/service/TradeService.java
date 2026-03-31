package com.tradehub.service;

import com.tradehub.model.Portfolio;
import com.tradehub.model.Transaction;
import com.tradehub.repository.PortfolioRepository;
import com.tradehub.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeService {

    private final PortfolioRepository portfolioRepo;
    private final TransactionRepository transactionRepo;
    private final PriceService priceService;

    public TradeService(PortfolioRepository portfolioRepo,
                        TransactionRepository transactionRepo,
                        PriceService priceService) {

        this.portfolioRepo = portfolioRepo;
        this.transactionRepo = transactionRepo;
        this.priceService = priceService;
    }

    // 🔥 BUY
    public Portfolio buy(Long userId, String coin, double qty) {

        double price = priceService.getPrice(coin);

        Portfolio p = portfolioRepo.findByUserIdAndCoinName(userId, coin);

        if (p == null) {
            p = new Portfolio();
            p.setUserId(userId);
            p.buy(coin, qty);   // ✅ USING METHOD
        } else {
            p.buy(coin, qty);   // ✅ USING METHOD
        }

        portfolioRepo.save(p);

        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setCoin(coin);
        t.setQuantity(qty);
        t.setType("BUY");
        t.setPrice(price);
        t.setTime(LocalDateTime.now());

        transactionRepo.save(t);

        return p;
    }

    // 🔥 SELL
    public Portfolio sell(Long userId, String coin, double qty) {

        double price = priceService.getPrice(coin);

        Portfolio p = portfolioRepo.findByUserIdAndCoinName(userId, coin);

        if (p != null) {

            p.sell(coin, qty);  // ✅ USING METHOD
            portfolioRepo.save(p);

            Transaction t = new Transaction();
            t.setUserId(userId);
            t.setCoin(coin);
            t.setQuantity(qty);
            t.setType("SELL");
            t.setPrice(price);
            t.setTime(LocalDateTime.now());

            transactionRepo.save(t);

            return p;
        }

        return null;
    }

    // 🔥 PORTFOLIO VALUE
    public double getPortfolioValue(Long userId) {

        double total = 0;

        List<Portfolio> list = portfolioRepo.findByUserId(userId);

        for (Portfolio p : list) {
            double price = priceService.getPrice(p.getCoinName());
            total += p.getQuantity() * price;
        }

        return total;
    }

    // 🔥 TOTAL INVESTMENT
    public double getTotalInvestment(Long userId) {

        double total = 0;

        List<Transaction> list = transactionRepo.findByUserId(userId);

        for (Transaction t : list) {

            if (t.getType().equals("BUY")) {
                total += t.getQuantity() * t.getPrice();
            } else {
                total -= t.getQuantity() * t.getPrice();
            }
        }

        return total;
    }

    // 🔥 PROFIT / LOSS
    public double getProfitOrLoss(Long userId) {
        return getPortfolioValue(userId) - getTotalInvestment(userId);
    }
}