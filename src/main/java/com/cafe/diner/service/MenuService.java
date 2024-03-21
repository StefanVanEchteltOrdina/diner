package com.cafe.diner.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.openapitools.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private DrinkService drinkService;
    private FoodService foodService;

    public List<MenuItem> all() {
        List<MenuItem> allItems = new ArrayList<>();
        allItems.addAll(drinkService.all());
        allItems.addAll(foodService.all());

        return allItems;
    }
}
