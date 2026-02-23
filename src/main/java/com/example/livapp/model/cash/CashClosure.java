package com.example.livapp.model.cash;

import com.example.livapp.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "cash_closure")
public class CashClosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "cashier_id", nullable = false)
    private User cashier;


    private double amountReceived;
    private double amountWithdrawn;
    private String description;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date closureDate;


}
