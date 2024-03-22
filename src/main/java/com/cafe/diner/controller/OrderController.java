package com.cafe.diner.controller;

import com.cafe.diner.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

    @PostMapping
    public ResponseEntity<ResponseOrder> placeOrderUsingPOST(@RequestBody RequestedItem requestedItem) {
        // TODO implement

        ResponseEntity response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        response = new ResponseEntity<>(HttpStatus.OK);
        response = new ResponseEntity<>(HttpStatus.CREATED);

        return response;
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

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/order/{id}/serve/dishes"
    )
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


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/order/{id}/serve/drinks"
    )
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
