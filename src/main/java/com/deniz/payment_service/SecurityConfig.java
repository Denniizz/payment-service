package com.deniz.payment_service;

import com.deniz.payment_service.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/register", "/login", "/styles.css").permitAll() // public URLs
                        .anyRequest().authenticated() // everything else requires login
                )
                .formLogin(form -> form
                        .loginPage("/login")          // custom login page
                        .defaultSuccessUrl("/products", true) // redirect after successful login
                        .permitAll()                  // login page is public
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")         // logout URL
                        .logoutSuccessUrl("/")        // redirect after logout
                        .permitAll()                  // logout is public
                );

        return http.build();
    }


    // Fetches from the DB
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository)
    {
        return username -> userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
