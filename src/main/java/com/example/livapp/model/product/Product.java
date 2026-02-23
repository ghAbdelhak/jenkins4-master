package com.example.livapp.model.product;

import com.example.livapp.validator.QuantityReservedConstraint;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "products")
@QuantityReservedConstraint(quantity = "quantity", reservedQuantity = "reservedQuantity", message = "La quantité réservée doit être inférieure à la quantité du produit")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull
    @NotBlank
    private String barCode;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    private String mark;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    private String model;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    private String color;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)


    private String type;

    private String description;

    private double price = 0.0;


    private int quantity = 0;
    private int reservedQuantity = 0;
    private int reservedPacks = 0;
    private int reservedOrders = 0;
    private boolean state = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public boolean isState() {
        return state;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getReservedQuantity() {
        return reservedQuantity;
    }


    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public int getReservedPacks() {
        return reservedPacks;
    }

    public void setReservedPacks(int reservedPacks) {
        this.reservedPacks = reservedPacks;
    }

    public int getReservedOrders() {
        return reservedOrders;
    }

    public void setReservedOrders(int reservedOrders) {
        this.reservedOrders = reservedOrders;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


}
