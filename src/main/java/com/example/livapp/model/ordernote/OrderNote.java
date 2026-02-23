package com.example.livapp.model.ordernote;

import jakarta.persistence.MappedSuperclass;

import jakarta.persistence.*;
import java.util.Date;

@MappedSuperclass
public class OrderNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private int totalQuantity;


    private int totalQuantityPack;

    private Double totalPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creationDate;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalQuantityPack() {
        return totalQuantityPack;
    }

    public void setTotalQuantityPack(int totalQuantityPack) {
        this.totalQuantityPack = totalQuantityPack;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
