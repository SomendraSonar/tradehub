package com.tradehub.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    

    @GetMapping("/api/test")
    public String testApi() {
        return "✅ API is working successfully!";
    }
}