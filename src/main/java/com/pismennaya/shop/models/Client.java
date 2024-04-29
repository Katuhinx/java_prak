package com.pismennaya.shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;
}
