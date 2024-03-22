package com.cafe.diner.service;

import com.cafe.diner.domain.OrderModel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

        // Check dat de order bestaat
        if (orderRepository.findById(orderId).isEmpty()) {
            return false;
        }

        OrderModel order = orderRepository.findById(orderId).get();

        // pas order in DB aan dat het eten uitgeserveerd is
        // als de drankjes al uitgeserveerd zijn, óf er zijn geen drankjes besteld,
        // dan gaat de status naar Billing
        if (order.getStatus() == Order.StatusEnum.DRINK_SERVED)
        {
            order.setStatus(Order.StatusEnum.BILLING);
        } else {
            order.setStatus(Order.StatusEnum.FOOD_SERVED);
        }
        orderRepository.save(order);
        return true;
    }

    public boolean serveDrinks(long orderId) {

        // Check dat de order bestaat
        if (orderRepository.findById(orderId).isEmpty()) {
            return false;
        }

        OrderModel order = orderRepository.findById(orderId).get();

        // pas order in DB aan dat het drinken uitgeserveerd is
        // als het eten al uitgeserveerd is, óf er is geen eten besteld,
        // dan gaat de status naar Billing

        if (order.getStatus() == Order.StatusEnum.FOOD_SERVED)
        {
            order.setStatus(Order.StatusEnum.BILLING);
        } else {
            order.setStatus(Order.StatusEnum.DRINK_SERVED);
        }
        orderRepository.save(order);
        return true;
    }


    private Order convert(OrderModel orderModel) {
        // Todo: uitbreiden met alle velden.

        Order order =  new Order();
        order.setId(orderModel.getId());
        return order;
    }

    public ResponseEntity<ResponseOrder> placeOrder(List<RequestedItem> requestedItems){
        ResponseEntity<ResponseOrder> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> foodResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> drinkResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Sla initieel de order op om een ID te genereren
        OrderModel order = new OrderModel();
        order.setStatus(Order.StatusEnum.INITIAL);
        order = orderRepository.save(order);

        //Dishes doorsturen naar cafe
        List<RequestedItem> requestedDishes = requestedItems.stream()
                .filter(item -> item.getType() == RequestedItem.TypeEnum.DISH).toList();
        if(!requestedDishes.isEmpty()){
            foodResponse = foodService.sendOrder(requestedDishes, order.getId());
        }

        //Drinks doorsturen naar bar
        List<RequestedItem> requestedDrinks = requestedItems.stream()
                .filter(item -> item.getType() == RequestedItem.TypeEnum.DRINK).toList();
        if(!requestedDrinks.isEmpty()) {
            drinkResponse = drinkService.sendOrder(requestedDrinks, order.getId());
        }

        // Is de bestelling geaccepteerd?
        if( foodResponse.getStatusCode().isSameCodeAs(HttpStatus.ACCEPTED) && drinkResponse.getStatusCode().isSameCodeAs(HttpStatus.ACCEPTED)){
            response = new ResponseEntity<>(HttpStatus.ACCEPTED);
            order.setStatus(Order.StatusEnum.PREPARING);
        }

        System.out.println(order.toString());

        // Sla de order op in de DB
        orderRepository.save(order);

        return response;
    }
}
