package com.example.livapp.service.implementation.order;

import com.example.livapp.model.order.Order;
import com.example.livapp.model.order.OrderLine;
import com.example.livapp.repository.order.OrderLinesRepository;
import com.example.livapp.service.abstraction.order.OrderLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLinesServiceImpl implements OrderLinesService {

    private final OrderLinesRepository orderLinesRepository;

    @Autowired
    public OrderLinesServiceImpl(OrderLinesRepository orderLinesRepository) {
        this.orderLinesRepository = orderLinesRepository;
    }


    @Override
    public List<OrderLine> getOrderLines(Order order) {
        return orderLinesRepository.findOrderLinesByOrder(order);
    }


}

