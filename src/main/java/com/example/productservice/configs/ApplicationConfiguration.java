package com.example.productservice.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration   
public class ApplicationConfiguration {
    @Bean
    public ModelMapper createmodelmapper()
    {
        return new ModelMapper();
    }
}
