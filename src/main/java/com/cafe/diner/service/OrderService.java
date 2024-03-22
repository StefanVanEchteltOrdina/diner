package com.cafe.diner.service;

import com.cafe.diner.domain.OrderModel;
import org.aspectj.weaver.ast.Or;
import org.openapitools.model.Order;
import com.cafe.diner.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public List<Order> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    public Optional<Order> find(Long id) {
        Optional<OrderModel> orderModel = orderRepository.findById(id);

        return orderModel.map(this::convert);
    }


    private Order convert(OrderModel orderModel) {
        // Todo: uitbreiden met alle velden.

        Order order =  new Order();
        order.setId(orderModel.getId());
        return order;
    }
}
