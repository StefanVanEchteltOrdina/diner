package com.cafe.diner.service;

import com.cafe.diner.domain.OrderModel;
import org.openapitools.model.Order;
import com.cafe.diner.repository.OrderRepository;
import org.openapitools.model.RequestedItem;
import org.openapitools.model.ResponseOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private DrinkService drinkService;
    private FoodService foodService;

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

    public ResponseEntity<ResponseOrder> placeOrder(List<RequestedItem> requestedItems){
        ResponseEntity response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //TODO: Orders opslaan
        long tempOrderId = 1;

        //Dishes doorsturen naar cafe
        List<RequestedItem> requestedDishes = requestedItems.stream()
                .filter(item -> item.getType() == RequestedItem.TypeEnum.DISH)
                .toList();
        ResponseEntity foodResponse = foodService.sendOrder(requestedDishes, tempOrderId);

        //Drinks doorsturen naar bar
        List<RequestedItem> requestedDrinks = requestedItems.stream()
                .filter(item -> item.getType() == RequestedItem.TypeEnum.DRINK)
                .toList();

        ResponseEntity drinkResponse = drinkService.sendOrder(requestedDrinks, tempOrderId);

        return response;
    }
}
