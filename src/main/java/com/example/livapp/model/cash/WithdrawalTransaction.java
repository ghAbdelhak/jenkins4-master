package com.example.livapp.model.cash;


import com.example.livapp.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "withdrawal_transactions")
public class WithdrawalTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date withdrawalDate;

    @OneToOne
    @JoinColumn(name = "cashier_id", nullable = false)
    private User cashier;

    private String description;




}
