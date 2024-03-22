package com.cafe.diner.service;

import com.cafe.diner.config.DinerConfig;
import com.cafe.diner.external.ExternalMenuItem;
import lombok.AllArgsConstructor;
import org.openapitools.model.MenuItem;
import org.openapitools.model.RequestedItem;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FoodService {

    private DinerConfig dinerConfig;
    private RestTemplate restTemplate;

    public List<MenuItem> all() {
        System.out.println("Get menu from kitchen");

        String url = dinerConfig.getKitchenUrl() + "/api/menu/";

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

    public ResponseEntity<Void> sendOrder(List<RequestedItem> requestedItems, long orderId ){
        System.out.println("Send order to kitchen");
        String url = dinerConfig.getKitchenUrl() + "/api/order/";

        Map<String, Object> payload = new HashMap<>();
        payload.put("items", requestedItems);
        payload.put("orderid", orderId);

        return restTemplate.postForEntity(url, payload, Void.class);
    }
}
