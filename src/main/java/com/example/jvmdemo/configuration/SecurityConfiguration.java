package com.example.jvmdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/actuator/**",
                                "/v1/iban/checkIban",
                                "/v1/iban/checkIbanList",
                                "/v1/iban/generateTestData").permitAll()
                        .anyRequest().denyAll()
                );

        http.cors().and().csrf().disable();
        return http.build();
    }
}
