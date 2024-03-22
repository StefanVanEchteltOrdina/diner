package com.cafe.diner.domain;

import jakarta.persistence.*;
import lombok.*;
import org.openapitools.model.Order;

import java.util.List;

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

    @OneToMany(mappedBy = "orderId")
    private List<OrderItemModel> orderItems;
}
