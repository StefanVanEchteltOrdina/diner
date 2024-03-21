package com.cafe.diner.controller;

import com.cafe.diner.service.OrderService;
import org.openapitools.api.ApiApi;
import org.openapitools.model.Order;
import org.openapitools.model.ResponseOrder;
import org.openapitools.model.Bill;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController implements ApiApi {

    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrdersUsingGET() {
        List<Order> orders = orderService.getAll();

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseOrder> placeOrderUsingPOST() {
        // TODO implement

        ResponseEntity response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        response = new ResponseEntity<>(HttpStatus.OK);
        response = new ResponseEntity<>(HttpStatus.CREATED);

        return response;
    }

    @GetMapping("/{id}/bill")
    public ResponseEntity<Bill> getBillUsingGET() {
        // TODO implement

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TODO add push...

    @PostMapping("/{id}/serve/dishes")
    public void x4() {
        // TODO implement

    }

    @PostMapping("/{id}/serve/drinks")
    public void x5() {
        // TODO implement

    }
}
