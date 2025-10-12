package com.grannsnack.GrannSnack.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // passt zu BCrypt-Hashes, die mit $2a/$2b/$2y beginnen
        return new BCryptPasswordEncoder();
    }
}

