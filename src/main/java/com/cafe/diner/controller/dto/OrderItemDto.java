package com.cafe.diner.controller.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private MenuItemType type;
    private boolean prepared;
    private long quantity;
    private String name;
    private long id;
}
