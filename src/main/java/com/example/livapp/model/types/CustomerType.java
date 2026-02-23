package com.example.livapp.model.types;

public enum CustomerType {
    RETAILER("Retailer"),
    BIG_ACCOUNT("Big Account");


    private final String type;

    CustomerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}