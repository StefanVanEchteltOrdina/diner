package com.cafe.diner.service;

import com.cafe.diner.config.DinerConfig;
import com.cafe.diner.external.ExternalMenuItem;
import lombok.AllArgsConstructor;
import org.openapitools.model.MenuItem;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {

    private DinerConfig dinerConfig;
    private RestTemplate restTemplate;

    public List<MenuItem> all() {
        String url = dinerConfig.getKitchenUrl() + "/api/menu";

        ResponseEntity<List<ExternalMenuItem>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ExternalMenuItem>>() {}
        );

        if (response.getBody() == null || !response.getStatusCode().is2xxSuccessful()) {
            return new ArrayList<>();
        }

        return response.getBody().stream()
            .map(m -> {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setId(m.getId());
                    menuItem.setDescription(m.getDescription());
                    menuItem.setName(m.getName());
                    menuItem.setPrice(m.getPrice());
                    menuItem.setType(MenuItem.TypeEnum.DISH);
                    return menuItem;
            }).toList();
    }
}
