package com.pismennaya.shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_product")
@Getter
@Setter
@NoArgsConstructor
public class OrderProduct implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;
}
