package com.grannsnack.GrannSnack.Configuration;

import com.grannsnack.GrannSnack.Service.DBUserService;
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

/**
 * This class is responsible for handling the security layer of the web application. All requests to the backend go through this
 * configuration. This is to make sure that only users get access to user information and not admin information. It's also used to
 * change how we handle cors and csrf configurations.
 * @Author Joel Seger och Priscilla Wettlén
 */
@Configuration
@EnableWebSecurity
public class HTTPSecurityConfig{

    @Autowired
    DBUserService dbUserService;

    @Autowired CustomCorsConfig customCorsConfig;

    /**
     * This method is used to configure the security chain in our web application. The filter which every request to the backend goes through.
     * @param httpSecurity this is the configuration file used to send back to spring boot.
     * @return the HttpSecurity as a config for spring
     * @throws Exception in case the rules are not followed an Exception is thrown.
     */
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

    /**
     * This method provides the program with an authentication provider that is used by spring and spring security to
     * verify a user when logging in.
     * @return an authentication provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(dbUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * This method returns a password encryption encoder. Spring uses this to encrypt the password of newly created users.
     * @return a password encryption encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
