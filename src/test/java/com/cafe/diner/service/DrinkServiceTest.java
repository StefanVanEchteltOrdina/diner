package com.cafe.diner.service;

import com.cafe.diner.config.DinerConfig;
import org.openapitools.model.MenuItem;
import com.cafe.diner.external.ExternalMenuItem;
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

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DrinkServiceTest {

    @InjectMocks
    private DrinkService drinkService;

    @Mock
    private DinerConfig dinerConfig;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void validate_that_drink_menu_is_returned() {
        String url = "localhost:8000/";
        List<ExternalMenuItem> returnData = new ArrayList<>();
        returnData.add(new ExternalMenuItem(1L, "description", "name", 1, 2L));

        ResponseEntity callResponse = new ResponseEntity<>(returnData, HttpStatusCode.valueOf(200));

        when(dinerConfig.getBarUrl()).thenReturn(url);
        when(restTemplate.exchange(url + "/api/menu", HttpMethod.GET, null, new ParameterizedTypeReference<List<ExternalMenuItem>>() {}))
                .thenReturn(callResponse);

        List<MenuItem> response = drinkService.all();

        MenuItem item1 = response.get(0);
        assertThat(item1.getId()).isEqualTo(1L);
        assertThat(item1.getDescription()).isEqualTo("description");
        assertThat(item1.getName()).isEqualTo("name");
        assertThat(item1.getPrice()).isEqualTo(2);
        assertThat(item1.getType()).isEqualTo(MenuItem.TypeEnum.DRINK);
    }

    @Test
    void validate_that_empty_result_is_returned() {
        String url = "http://localhost:8000";

        ResponseEntity callResponse = new ResponseEntity<>(new ArrayList<>(), HttpStatusCode.valueOf(500));

        when(dinerConfig.getBarUrl()).thenReturn(url);
        when(restTemplate.exchange(url + "/api/menu", HttpMethod.GET, null, new ParameterizedTypeReference<List<ExternalMenuItem>>() {}))
                .thenReturn(callResponse);

        List<MenuItem> response = drinkService.all();

        assertThat(response).isEmpty();
    }
}
