package com.pismennaya.shop.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.sql.Date;

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
    private Client client_id;

    @Column(nullable = false)
    private Date order_date;

    @Column(nullable = false)
    private Date delivery_date;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    public Order(Client client, Date delivery_date, String address, String status) {
        this.client_id = client;
        this.delivery_date = delivery_date;
        this.address = address;
        this.status = status;
    }
}
