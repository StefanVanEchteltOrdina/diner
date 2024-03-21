package com.cafe.diner.service;

import com.cafe.diner.controller.dto.OrderDto;
import com.cafe.diner.domain.Order;
import com.cafe.diner.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private OrderDto convert(Order order) {
        // Todo: uitbreiden met alle velden.
        return new OrderDto(order.getId());
    }
}
