package com.cafe.diner.service;

import com.cafe.diner.config.DinerConfig;
import com.cafe.diner.controller.dto.MenuItemDto;
import com.cafe.diner.controller.dto.MenuItemType;
import com.cafe.diner.domain.MenuItem;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrinkService {

    private DinerConfig dinerConfig;

    private RestTemplate restTemplate = new RestTemplate();

    DrinkService(final DinerConfig dinerConfig) {
        this.dinerConfig = dinerConfig;
    }

    public List<MenuItemDto> all() {
        String url = dinerConfig.getBarUrl() + "/api/menu";

        ResponseEntity<List<MenuItem>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MenuItem>>() {}
        );

        if (response.getBody() == null || !response.getStatusCode().is2xxSuccessful()) {
            return new ArrayList<>();
        }

        return response.getBody().stream()
                .map(m -> new MenuItemDto(m.getId(), m.getDescription(), m.getName(), m.getPrice(), MenuItemType.DRINK))
                .toList();
    }

}
