package com.cafe.diner.controller;

import com.cafe.diner.service.MenuService;
import com.cafe.diner.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import org.openapitools.api.ApiApi;
import org.openapitools.model.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderController implements ApiApi {

    private OrderService orderService;
    private MenuService menuService;

    @Override
    public ResponseEntity<List<MenuItem>> getMenuUsingGET() {
        List<MenuItem> menuItems = menuService.all();

        if (menuItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Order>> getOrdersUsingGET(

    ) {
        List<Order> orders = orderService.getAll();

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseOrder> placeOrderUsingPOST(
            @Parameter(name = "requestedItems", description = "the requested items food and drinks", required = true) @Valid @RequestBody List<@Valid RequestedItem> requestedItems
    ) {
        return orderService.placeOrder(requestedItems);
    }

    @Override
    public ResponseEntity<Bill> getBillUsingGET(
            @Parameter(name = "id", description = "order id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        Optional<Order> order = orderService.find(id);

        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // TODO get bill

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TODO add push...

    @Override
    public ResponseEntity<Void> serveDishesUsingPOST(
            @Parameter(name = "id", description = "order id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        System.out.println("Serving dishes.");
        boolean gelukt = orderService.serveDishes(id);

        if (gelukt) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<Void> serveDrinksUsingPOST(
            @Parameter(name = "id", description = "order id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        System.out.println("Serving drinks");
        boolean gelukt = orderService.serveDrinks(id);

        if (gelukt) {
        return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
