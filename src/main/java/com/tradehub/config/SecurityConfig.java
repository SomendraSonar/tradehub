package com.tradehub.config;

import com.tradehub.security.JwtFilter;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/auth/**",      // ✅ login/register
                        "/prices",       // ✅ public API
                        "/trade/**",     // ✅ allow for now
                        "/portfolio/**"  // ✅ allow for now
                ).permitAll()

                .anyRequest().permitAll() // 🔥 TEMP: allow everything
            );

        return http.build();
    }
}