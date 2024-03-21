package com.cafe.diner.controller.dto;

import lombok.Data;

@Data
public class MenuItemDto {
    private int id;
    private String description;
    private String name;
    private double price;
    private MenuItemType type;
}
