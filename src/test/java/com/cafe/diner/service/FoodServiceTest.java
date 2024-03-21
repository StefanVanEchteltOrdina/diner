package com.cafe.diner.service;

import com.cafe.diner.config.DinerConfig;
import com.cafe.diner.controller.dto.MenuItemDto;
import com.cafe.diner.controller.dto.MenuItemType;
import com.cafe.diner.domain.MenuItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @InjectMocks
    private FoodService foodService;

    @Mock
    private DinerConfig dinerConfig;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void validate_that_drink_menu_is_returned() {
        String url = "localhost:8000/";
        List<MenuItem> returnData = new ArrayList<>();
        returnData.add(new MenuItem(1L, "description", "name", 1, 2));

        ResponseEntity callResponse = new ResponseEntity<>(returnData, HttpStatusCode.valueOf(200));

        when(dinerConfig.getKitchenUrl()).thenReturn(url);
        when(restTemplate.exchange(url + "/api/menu", HttpMethod.GET, null, new ParameterizedTypeReference<List<MenuItem>>() {}))
                .thenReturn(callResponse);

        List<MenuItemDto> response = foodService.all();

        MenuItemDto item1 = response.get(0);
        assertThat(item1.getId()).isEqualTo(1L);
        assertThat(item1.getDescription()).isEqualTo("description");
        assertThat(item1.getName()).isEqualTo("name");
        assertThat(item1.getPrice()).isEqualTo(2);
        assertThat(item1.getType()).isEqualTo(MenuItemType.DISH);
    }

    @Test
    void validate_that_empty_result_is_returned() {
        String url = "localhost:8000/";

        ResponseEntity callResponse = new ResponseEntity<>(new ArrayList<>(), HttpStatusCode.valueOf(500));

        when(dinerConfig.getKitchenUrl()).thenReturn(url);
        when(restTemplate.exchange(url + "/api/menu", HttpMethod.GET, null, new ParameterizedTypeReference<List<MenuItem>>() {}))
                .thenReturn(callResponse);

        List<MenuItemDto> response = foodService.all();

        assertThat(response).isEmpty();
    }
}
