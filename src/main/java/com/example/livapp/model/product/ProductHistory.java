package com.example.livapp.model.product;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity(name = "product_history")
public class ProductHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String changedBy = "";
    private double price;
    private int quantity;
    private int reservedQuantity;
    private int reservedPacks;
    private int reservedOrders;
    private boolean state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date changeDate;

    private String action;


}
