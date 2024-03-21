package com.cafe.diner.service;

import com.cafe.diner.controller.dto.MenuItemDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private DrinkService drinkService;
    private FoodService foodService;

    public List<MenuItemDto> all() {
        List<MenuItemDto> allItems = new ArrayList<>();
        allItems.addAll(drinkService.all());
        //allItems.addAll(foodService.all());

        return allItems;
    }
}
