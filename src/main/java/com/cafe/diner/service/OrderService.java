package com.cafe.diner.service;

import com.cafe.diner.domain.OrderItemModel;
import com.cafe.diner.domain.OrderModel;
import com.cafe.diner.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Order;
import com.cafe.diner.repository.OrderRepository;
import org.openapitools.model.OrderItem;
import org.openapitools.model.RequestedItem;
import org.openapitools.model.ResponseOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
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

    private OrderItemModel convertOrderItemToDomain(RequestedItem requestedItem, Long orderId) {
        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setOrderId(orderId);
        orderItemModel.setProductId(requestedItem.getId());
        orderItemModel.setName(requestedItem.getName());
        orderItemModel.setQuantity(requestedItem.getQuantity());
        orderItemModel.setItemType(requestedItem.getType());
        orderItemModel.setPrice(1L);
        return orderItemModel;
    }

    private OrderItem convertFromOrderItem(OrderItemModel orderItemModel) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemModel.getId());
        orderItem.setName(orderItemModel.getName());
        orderItem.setQuantity(orderItemModel.getQuantity());
        return orderItem;
    }

    private Order convert(OrderModel orderModel) {
        Order order =  new Order();
        order.setId(orderModel.getId());
        order.setStatus(orderModel.getStatus());
        List<OrderItem> orderItems = orderModel.getOrderItems().stream().map(this::convertFromOrderItem).toList();
        order.setOrderItems(orderItems);
        return order;
    }

    public ResponseEntity<ResponseOrder> placeOrder(List<RequestedItem> requestedItems){
        ResponseEntity<ResponseOrder> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> foodResponse = new ResponseEntity<>(HttpStatus.ACCEPTED);
        ResponseEntity<Void> drinkResponse = new ResponseEntity<>(HttpStatus.ACCEPTED);

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

        OrderModel finalOrder = order;
        requestedItems.stream()
                        .map(i -> this.convertOrderItemToDomain(i, finalOrder.getId()))
                        .forEach(o -> orderItemRepository.save(o));

        // Is de bestelling geaccepteerd?
        if( foodResponse.getStatusCode().isSameCodeAs(HttpStatus.ACCEPTED) && drinkResponse.getStatusCode().isSameCodeAs(HttpStatus.ACCEPTED)){
            order.setStatus(Order.StatusEnum.PREPARING);

            // Sla de order op in de DB
            orderRepository.save(order);

            ResponseOrder responseOrder = new ResponseOrder();

            responseOrder.setBillUrl("/api/order/"+order.getId()+"/bill");
            responseOrder.setOrderid(order.getId());

            response = new ResponseEntity<>(responseOrder, HttpStatus.ACCEPTED);
        }

        log.info(order.toString());

        return response;
    }
}
