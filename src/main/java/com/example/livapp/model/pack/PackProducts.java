package com.example.livapp.model.pack;


import com.example.livapp.model.product.Product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "pack_products")

public class PackProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "pack_id", nullable = false)
    private Pack pack;

    private int quantity;

}
