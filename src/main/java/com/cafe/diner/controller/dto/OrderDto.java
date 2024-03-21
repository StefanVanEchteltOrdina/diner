package com.cafe.diner.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    final private long id;
    // Todo: onderstaande final
    private List<OrderItemDto> orderItems;
    private OrderStatus status;
}
