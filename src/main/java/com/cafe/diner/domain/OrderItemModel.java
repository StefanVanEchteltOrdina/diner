package com.cafe.diner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.model.RequestedItem;

@Entity
@Data
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long orderId;

    private Long productId;

    private String name;

    private Long quantity;

    private Long price;

    private RequestedItem.TypeEnum itemType;
}
