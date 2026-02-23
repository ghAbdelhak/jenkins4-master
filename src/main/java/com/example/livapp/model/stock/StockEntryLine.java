package com.example.livapp.model.stock;

import com.example.livapp.model.product.Product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "stock_entry_lines")
public class StockEntryLine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "stock_entry_id", nullable = false)
    private StockEntry stockEntry;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private double price;
    private int quantity;


}
