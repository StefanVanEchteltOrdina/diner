package com.cafe.diner.controller;

import com.cafe.diner.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.openapitools.api.ApiApi;
import org.openapitools.model.Order;
import org.openapitools.model.ResponseOrder;
import org.openapitools.model.RequestedItem;
import org.openapitools.model.Bill;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @Override
    public ResponseEntity<ResponseOrder> placeOrderUsingPOST(@Parameter(name = "requestedItems",
            description = "the requested items food and drinks", required = true) @Valid @RequestBody List<@Valid RequestedItem> requestedItems
    ) {

        return orderService.placeOrder(requestedItems);
    }

    @GetMapping("/{id}/bill")
    public ResponseEntity<Bill> getBillUsingGET(@PathVariable Long id) {
        Optional<Order> order = orderService.find(id);

        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // TODO get bill

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
