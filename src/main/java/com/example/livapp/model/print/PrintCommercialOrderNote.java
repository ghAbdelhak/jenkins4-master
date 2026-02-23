/*
package com.example.livapp.model.print;


import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.order.Order;
import jakarta.persistence.*;

@Entity(name = "print_commercial_order_notes")
public class PrintCommercialOrderNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "commercial_order_id", nullable = false)
    private CommercialOrder commercialOrder;

    private double totalPrice;
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CommercialOrder getCommercialOrder() {
        return commercialOrder;
    }

    public void setCommercialOrder(CommercialOrder commercialOrder) {
        this.commercialOrder = commercialOrder;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
*/
