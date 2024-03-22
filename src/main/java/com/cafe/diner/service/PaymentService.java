package com.cafe.diner.service;

import lombok.AllArgsConstructor;
import org.openapitools.model.Bill;
import org.openapitools.model.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {

    private OrderService orderService;
    private MenuService menuService;

    public Bill getBillOrderId(Long id) {
        Optional<Order> order = orderService.find(id);
        if (order.isEmpty()) {
            return null;
        }

        Bill bill = new Bill();
        bill.setTotal(10L);
        bill.setPayUrl("betaal hier http://test.nl");

        return bill;
    }
}
