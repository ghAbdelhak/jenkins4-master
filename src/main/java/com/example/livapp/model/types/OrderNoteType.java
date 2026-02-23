package com.example.livapp.model.types;


public enum OrderNoteType {

    PURCHASE("Purchase"),
    EXIT("Exit"),
    DELIVERY("Delivery");
    private final String type;

    OrderNoteType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}