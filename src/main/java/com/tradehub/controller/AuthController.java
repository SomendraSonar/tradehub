package com.tradehub.controller;

import com.tradehub.model.User;
import com.tradehub.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) {
        return service.register(body.get("username"), body.get("password"));
    }

    // LOGIN
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {

        User user = service.login(body.get("username"), body.get("password"));

        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            response.put("message", "Login successful");
            response.put("userId", user.getId());
        } else {
            response.put("message", "Invalid credentials");
        }

        return response;
    }
}