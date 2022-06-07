package com.example.jvmdemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/actuator/**").permitAll()
                .mvcMatchers("/v1/iban/checkIban").permitAll()
                .mvcMatchers("/v1/iban/checkIbanList").permitAll()
                .mvcMatchers("/v1/iban/generateTestData").permitAll()
                .anyRequest().denyAll();

        http.cors().and().csrf().disable();
    }
}
