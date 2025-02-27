package com.ecommerce.demo.configurations;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplateBuilder().build();
    }

}
