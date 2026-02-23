package com.example.livapp.model.commercialordernote;

import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;
import jakarta.persistence.*;

@Entity(name ="commercial_order_note_lines")
public class CommercialOrderNoteLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "commercial_exit_order_note_id")
    private ExitCommercialOrderNote exitCommercialOrderNote;

    @ManyToOne
    @JoinColumn(name = "commercial_purchase_order_note_id")
    private PurchaseCommercialOrderNote purchaseCommercialOrderNote;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;

    private double price;

    private int quantity;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }





    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ExitCommercialOrderNote getExitCommercialOrderNote() {
        return exitCommercialOrderNote;
    }

    public void setExitCommercialOrderNote(ExitCommercialOrderNote exitCommercialOrderNote) {
        this.exitCommercialOrderNote = exitCommercialOrderNote;
    }

    public PurchaseCommercialOrderNote getPurchaseCommercialOrderNote() {
        return purchaseCommercialOrderNote;
    }

    public void setPurchaseCommercialOrderNote(PurchaseCommercialOrderNote purchaseCommercialOrderNote) {
        this.purchaseCommercialOrderNote = purchaseCommercialOrderNote;
    }
}
