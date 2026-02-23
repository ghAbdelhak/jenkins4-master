package com.example.livapp.model.commercialorder;


import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "commercial_order_lines")
public class CommercialOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "commercial_order_id", nullable = false)
    private CommercialOrder commercialOrder;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double price;

    private int quantity;



}
