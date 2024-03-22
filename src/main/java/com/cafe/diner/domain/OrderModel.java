package com.cafe.diner.domain;

import jakarta.persistence.*;
import lombok.*;
import org.openapitools.model.Order;
import org.openapitools.model.OrderItem;

@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Order.StatusEnum status;

    // Todo: orderItems en status toevoegen.
}
