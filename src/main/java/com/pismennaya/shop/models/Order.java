package com.pismennaya.shop.models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private Date order_date;

    @Column(nullable = false)
    private Date shipping_date;

    @Column(nullable = false)
    private String address;

}
