package com.pismennaya.shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product implements CommonEntity<Long>  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

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
    private int warranty;
    private String description;

    public Product() {}

    public Product(Category category, String name, String production, String country, int price, int quantity, String description) {
        this.category = category;
        this.name = name;
        this.production = production;
        this.country = country;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }
}
