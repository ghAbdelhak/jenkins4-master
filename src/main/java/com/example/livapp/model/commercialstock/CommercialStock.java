package com.example.livapp.model.commercialstock;


import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "commercial_stock")
public class CommercialStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "commercial_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;

    private int quantity;



}
