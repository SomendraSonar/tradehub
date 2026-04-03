package com.tradehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradehubApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradehubApplication.class, args);
    }
}