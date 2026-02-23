package com.example.livapp.service.abstraction.order;

import com.example.livapp.model.order.Order;
import com.example.livapp.model.order.OrderLine;

import java.util.List;

public interface OrderLinesService {

    List<OrderLine> getOrderLines(Order order);


}
