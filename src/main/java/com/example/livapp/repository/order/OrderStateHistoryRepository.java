package com.example.livapp.repository.order;


import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialorder.CommercialOrderStateHistory;
import com.example.livapp.model.order.Order;
import com.example.livapp.model.order.OrderStateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStateHistoryRepository extends JpaRepository<OrderStateHistory, Long> {

    List<OrderStateHistory> findAllByOrder(Order Order);

}
