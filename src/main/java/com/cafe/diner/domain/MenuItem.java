package com.cafe.diner.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItem {
    private Long id;
    private String description;
    private String name;
    private int preparationTime;
    private int price;
}
