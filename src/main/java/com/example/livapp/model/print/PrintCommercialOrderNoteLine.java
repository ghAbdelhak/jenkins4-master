/*
package com.example.livapp.model.print;


import jakarta.persistence.*;

@Entity(name = "print_commercial_order_note_lines")
public class PrintCommercialOrderNoteLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "print_commercial_order_note_id", nullable = false)
    private PrintCommercialOrderNote printCommercialOrderNote;

    private String mark;
    private String model;
    private String color;
    private String packName;
    private double price;
    private int quantity;
    private double totalLinePrice;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
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


    public PrintCommercialOrderNote getPrintCommercialOrderNote() {
        return printCommercialOrderNote;
    }

    public void setPrintCommercialOrderNote(PrintCommercialOrderNote printCommercialOrderNote) {
        this.printCommercialOrderNote = printCommercialOrderNote;
    }

    public double getTotalLinePrice() {
        return totalLinePrice;
    }

    public void setTotalLinePrice(double totalLinePrice) {
        this.totalLinePrice = totalLinePrice;
    }
}
*/
