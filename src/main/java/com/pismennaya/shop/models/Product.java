package com.pismennaya.shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString

public class Product implements CommonEntity<Long>  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private int id_category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String production;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    private float weight;
    private float volume;
    private String size;
    private float diagonal;
    private String resolution;
    private String color;
    private int chamber;
    private int power;
    private String material;
    private int steam_suply;
    private float warranty;
    private String description;
}
