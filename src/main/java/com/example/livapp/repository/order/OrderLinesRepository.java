package com.example.livapp.repository.order;

import com.example.livapp.model.order.Order;
import com.example.livapp.model.order.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLinesRepository extends JpaRepository<OrderLine, Long> {

    List<OrderLine> findOrderLinesByOrder(Order order);
}
