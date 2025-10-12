package com.grannsnack;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class DevSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(h -> h.frameOptions(f -> f.disable())) // H2-Console
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/error",
                                 "/css/**", "/js/**", "/images/**",
                                 "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login")
                .usernameParameter("email")     // wichtig: passt zu login.html
                .passwordParameter("password")
                .defaultSuccessUrl("/userhome", true)
                .failureUrl("/login?error=true")
            )
            .logout(l -> l
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
            );
        return http.build();
    }
}
