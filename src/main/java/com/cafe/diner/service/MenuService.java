package com.cafe.diner.service;

import com.cafe.diner.controller.dto.MenuItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private DrinkService drinkService;
    private FoodService foodService;

    public List<MenuItemDto> all() {
        List<MenuItemDto> drinkItems = null;
        List<MenuItemDto> foodItems = null;


        return null;
    }
}
