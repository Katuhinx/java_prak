package com.pismennaya.shop.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDate;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
public class Order implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @CreatedDate
    private LocalDate order_date;

    @Column(nullable = false)
    private LocalDate delivery_date;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    public Order(Client client, LocalDate delivery_date, String address, String status) {
        this.client = client;
        this.delivery_date = delivery_date;
        this.address = address;
        this.status = status;
    }
}
