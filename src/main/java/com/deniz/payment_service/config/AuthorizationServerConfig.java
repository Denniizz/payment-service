package com.deniz.payment_service.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(1) // MUST run before your AppSecurityConfig
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception
    {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher()) // only match /oauth2/*
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))

                .with(authorizationServerConfigurer, config -> {});

        // Let OAuth server use your normal login page
        http.formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder encoder)
    {
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("my-client")
                .clientSecret(encoder.encode("my-secret"))
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/app")
                .scope("read")
                .scope("write")
                .build();

        return new InMemoryRegisteredClientRepository(client);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource()
    {
        RSAKey rsaKey = Jwks.generateRsa();
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

}
