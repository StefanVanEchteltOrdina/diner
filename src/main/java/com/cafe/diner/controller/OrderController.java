package com.cafe.diner.controller;

import com.cafe.diner.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    @GetMapping
    public void x() {

    }

    @PostMapping
    public void x2() {

    }

    @GetMapping("/{id}/bill")
    public void x3() {

    }

    @PostMapping("/{id}/serve/dishes")
    public void x4() {

    }

    @PostMapping("/{id}/serve/drinks")
    public void x5() {

    }


}
