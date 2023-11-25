package com.example.lab2.config;

import com.example.lab2.repository.RatingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class Config {
    @Bean
    @Scope("prototype")
    public RatingRepository ratingRepository() {
        return new RatingRepository();
    }
}
