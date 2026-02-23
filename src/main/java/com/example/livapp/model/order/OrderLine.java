package com.example.livapp.model.order;

import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "order_lines")
public class OrderLine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double price;

    private int quantity;


    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;


}

