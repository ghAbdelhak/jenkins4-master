package com.example.livapp.model.commercialorder;

import com.example.livapp.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "commercial_orders")
public class CommercialOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private double totalPrice ;

    private String state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date orderDate;

    private boolean archived = false;


}