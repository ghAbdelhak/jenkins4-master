package com.example.livapp.model.types;

public enum OrderState {
    PENDING("Pending"),
    PREPARING("Preparing"),
    OUT_FOR_DELIVERY("Out for Delivery"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    private final String state;

    OrderState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}