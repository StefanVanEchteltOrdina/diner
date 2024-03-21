package com.cafe.diner.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private long id;
    private List<OrderItemDto> orderItems;
    private OrderStatus status;
}
