package com.cafe.diner.controller;

import com.cafe.diner.controller.dto.MenuItemDto;
import com.cafe.diner.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {

    private MenuService menuService;

    @GetMapping
    public List<MenuItemDto> all() {
        return menuService.all();
    }

}
