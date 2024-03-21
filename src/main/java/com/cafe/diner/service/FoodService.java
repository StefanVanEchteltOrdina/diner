package com.cafe.diner.service;

import com.cafe.diner.config.DinerConfig;
import com.cafe.diner.controller.dto.MenuItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FoodService {
    private DinerConfig dinerConfig;

    public List<MenuItemDto> all() {
        RestTemplate restTemplate = new RestTemplate();

        String url = dinerConfig.getKitchenUrl() + "/api/menu";

        String response = restTemplate.getForObject(url, String.class);

        System.out.println("Response: " + response);

        // TODO: Return response as a list of MenuItemDto's

        return null;
    }
}
