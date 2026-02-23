package com.example.livapp.model.cash;

import com.example.livapp.model.customer.Customer;
import com.example.livapp.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "cash_register_transactions")
public class CashRegisterTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne
    @JoinColumn(name = "cashier_id", nullable = false)
    private User cashier;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date paymentDate;



}
