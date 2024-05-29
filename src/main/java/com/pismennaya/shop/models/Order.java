package com.pismennaya.shop.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @CreatedDate
    @Column(nullable = false)
    private Date order_date;

    @Column(nullable = false)
    private Date delivery_date;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "order_id")
    private Set<OrderProduct> orderProducts;

    public Order(Client client, Date delivery_date, String address, String status) {
        this.client = client;
        this.delivery_date = delivery_date;
        this.address = address;
        this.status = status;
    }
}
