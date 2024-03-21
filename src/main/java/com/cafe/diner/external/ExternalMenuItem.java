package com.cafe.diner.external;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalMenuItem {
    private Long id;
    private String description;
    private String name;
    private int preparationTime;
    private Long price;
}
