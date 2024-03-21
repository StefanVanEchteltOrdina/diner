package com.cafe.diner.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    // Todo: orderItems en status toevoegen.
}
