package com.example.jvmdemo.configuration;

import com.example.jvmdemo.observation.CustomObservationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ObservationRegistry createObservationRegistry() {
        ObservationRegistry registry = ObservationRegistry.create();
        registry.observationConfig().observationHandler(new CustomObservationHandler());
        return registry;
    }
}
