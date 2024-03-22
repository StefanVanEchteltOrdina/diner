package com.cafe.diner.controller;

import org.openapitools.model.MenuItem;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class OrderControllerIT extends BaseIT {

    @Test
    void validate_get_all_menu_items() throws Exception {
        mockRestServiceServer.expect(requestTo("http://localhost:8001/api/menu"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[{\"id\": \"1\", \"description\": \"drinkssss\",\"name\": \"drink\",\"price\": \"2\"}]", MediaType.APPLICATION_JSON));

        mockRestServiceServer.expect(requestTo("http://localhost:8002/api/menu"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[{\"id\": \"2\", \"description\": \"foodddsss\",\"name\": \"food\",\"price\": \"3\"}]", MediaType.APPLICATION_JSON));


        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<List<MenuItem>> response = restTemplate1.exchange(
                "http://localhost:8080/api/menu",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MenuItem>>() {}
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);

        MenuItem menuItem1 = response.getBody().get(0);
        assertThat(menuItem1.getId()).isEqualTo(1L);
        assertThat(menuItem1.getDescription()).isEqualTo("drinkssss");
        assertThat(menuItem1.getName()).isEqualTo("drink");
        assertThat(menuItem1.getPrice()).isEqualTo(2L);
        assertThat(menuItem1.getType()).isEqualTo(MenuItem.TypeEnum.DRINK);

        MenuItem menuItem2 = response.getBody().get(1);
        assertThat(menuItem2.getId()).isEqualTo(2L);
        assertThat(menuItem2.getDescription()).isEqualTo("foodddsss");
        assertThat(menuItem2.getName()).isEqualTo("food");
        assertThat(menuItem2.getPrice()).isEqualTo(3L);
        assertThat(menuItem2.getType()).isEqualTo(MenuItem.TypeEnum.DISH);
    }
}
