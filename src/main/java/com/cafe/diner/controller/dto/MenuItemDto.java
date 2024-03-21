package com.cafe.diner.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItemDto {
    private Long id;
    private String description;
    private String name;
    private long price;
    private MenuItemType type;
}
