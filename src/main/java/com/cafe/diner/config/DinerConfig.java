package com.cafe.diner.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Component
public class DinerConfig {

    @Value("${diner.kitchen.url}")
    private String kitchenUrl;

    @Value("${diner.bar.url}")
    private String barUrl;
}
