package com.example.livapp.model.ordernote;

import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name ="order_note_lines")
public class OrderNoteLine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "delivery_order_note_id")
    private DeliveryOrderNote deliveryOrderNote;

    @ManyToOne
    @JoinColumn(name = "purchase_order_note_id")
    private PurchaseOrderNote purchaseOrderNote;

    @ManyToOne
    @JoinColumn(name = "exit_order_note_id")
    private ExitOrderNote exitOrderNote;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;

    private double price;

    private int quantity;


}
