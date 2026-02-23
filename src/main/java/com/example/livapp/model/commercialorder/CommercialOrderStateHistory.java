package com.example.livapp.model.commercialorder;


import com.example.livapp.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "commercial_order_state_history")

public class CommercialOrderStateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "commercial_order_id", nullable = false)
    private CommercialOrder commercialOrder;


    @OneToOne
    @JoinColumn(name = "prepared_person_id")
    private User changedBy;


    private String oldState;

    private String newState;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date changedDate;



}
