package com.example.livapp.model.order;

import com.example.livapp.model.user.User;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "order_state_history")
public class OrderStateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    @OneToOne
    @JoinColumn(name = "prepared_person_id")
    private User changedBy;

    @OneToOne
    @JoinColumn(name = "delivery_person_id")
    private User deliveryPerson;

    private String oldState;

    private String newState;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date changedDate;



}
