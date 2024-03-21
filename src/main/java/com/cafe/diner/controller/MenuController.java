package com.cafe.diner.controller;

import com.cafe.diner.controller.dto.MenuItemDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {

    @GetMapping
    public List<MenuItemDto> all() {
        return null;
    }

}
