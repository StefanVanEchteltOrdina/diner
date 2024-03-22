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


    public boolean serveDishes(long orderId) {

        // TODO implement
        // Check dat de order bestaat
        if (false) {
            return false;
        }
        // pas order in DB aan dat het eten uitgeserveerd is
        // als de drankjes al uitgeserveerd zijn, óf er zijn geen drankjes besteld,
        // dan gaat de status naar Billing
        return true;
    }

    public boolean serveDrinks(long orderId) {

        // TODO implement
        // Check dat de order bestaat
        if (false) {
            return false;
        }
        // pas order in DB aan dat het drinken uitgeserveerd is
        // als het eten al uitgeserveerd is, óf er is geen eten besteld,
        // dan gaat de status naar Billing
        return true;
    }


    private Order convert(OrderModel orderModel) {
        // Todo: uitbreiden met alle velden.

        Order order =  new Order();
        order.setId(orderModel.getId());
        return order;
    }
}
