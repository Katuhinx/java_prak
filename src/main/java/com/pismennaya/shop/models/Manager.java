package com.pismennaya.shop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manager")
@Getter
@Setter
@NoArgsConstructor
public class Manager implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    public Manager(String login, String password) {
        this.login = login;
        this.password = password;
    }
}