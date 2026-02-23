package com.example.livapp.model.order;

import java.util.List;

public class OrderHolder {

    private Order order;

    private List<OrderLine> orderLines;


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
