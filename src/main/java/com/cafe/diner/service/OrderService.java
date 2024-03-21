package com.cafe.diner.service;

import com.cafe.diner.domain.OrderModel;
import org.openapitools.model.Order;
import com.cafe.diner.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private Order convert(OrderModel orderModel) {
        // Todo: uitbreiden met alle velden.

        Order order =  new Order();
        order.setId(orderModel.getId());
        return order;
    }
}
