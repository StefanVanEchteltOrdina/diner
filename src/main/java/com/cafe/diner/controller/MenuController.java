package com.cafe.diner.controller;

import org.openapitools.model.MenuItem;
import com.cafe.diner.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.openapitools.api.ApiApi;

@RestController
@AllArgsConstructor
public class MenuController implements ApiApi {

    private MenuService menuService;

    @Override
    public ResponseEntity<List<MenuItem>> getMenuUsingGET() {
        List<MenuItem> menuItems = menuService.all();

        if (menuItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(menuService.all(), HttpStatus.OK);
    }
}
