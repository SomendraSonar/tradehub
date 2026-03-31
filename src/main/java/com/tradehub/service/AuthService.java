package com.tradehub.service;

import com.tradehub.model.User;
import com.tradehub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    // REGISTER
    public String register(String username, String password) {

        if (repo.findByUsername(username) != null) {
            return "User already exists!";
        }

        repo.save(new User(username, password));
        return "User registered successfully!";
    }

    // LOGIN
    public User login(String username, String password) {

        User user = repo.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}