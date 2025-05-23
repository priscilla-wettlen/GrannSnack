package com.grannsnack.GrannSnack.Configuration;

import com.grannsnack.GrannSnack.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class HTTPSecurityConfig{

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired CustomCorsConfig customCorsConfig;


    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/u/laundry-booking/availability").hasRole("USER");
                    registry.requestMatchers("/u/laundry-booking/create").hasRole("USER");//access laundry booking without logging in
                    registry.requestMatchers("/u/**", "/default").hasRole("USER");
                    //registry.requestMatchers("/a/home/users").permitAll();

                    registry.requestMatchers("/u/**").hasRole("USER");
                    registry.requestMatchers("/a/**").hasRole("ADMIN");
                    registry.requestMatchers("/", "/home", "/login", "/register", "/index").permitAll();
                    registry.requestMatchers("/error", "/error/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/default", true))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(customCorsConfig))
                .build();
    }

    @Bean
    public MyUserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
