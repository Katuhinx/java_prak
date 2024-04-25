package com.pismennaya.shop.models;

import jakarta.persistence.*;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int id_order;

    @Column(nullable = false)
    private int id_product;

    @Column(nullable = false)
    private int quantity;
}
