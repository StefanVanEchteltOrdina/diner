package com.cafe.diner.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Getter
@Component
public class DinerConfig {

    @Value("${diner.kitchen.url}")
    private String kitchenUrl;

    @Value("${diner.bar.url}")
    private String barUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
