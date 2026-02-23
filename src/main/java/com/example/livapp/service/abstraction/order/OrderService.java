package com.example.livapp.service.abstraction.order;

import com.example.livapp.model.order.OrderStateHistory;
import com.example.livapp.model.user.User;
import com.example.livapp.model.order.Order;
import com.example.livapp.model.order.OrderHolder;
import com.example.livapp.model.types.OrderState;
import com.example.livapp.model.ordernote.ExitOrderNote;
import com.example.livapp.model.types.OrderNoteType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {


    Page<Order> findPaginated(int pageNo, int pageSize);


    OrderHolder getOrderDetails(long id);


    Order getOrder(long id);


    void updateOrderState(long orderId, OrderState state, User changedBy, User deliveryPerson);


    void exitOrder(ExitOrderNote exitOrderNote);


    void cancelOrder(Order order);


    void updateReservedOrderQuantities(long orderId, OrderNoteType orderNoteType);


    void archiveOrder(Order order);


    void archiveDeliveredOrders();


    int getUnprocessedClientOrders();


    List<OrderStateHistory> getOrderStateHistories(long OrderId);

}
