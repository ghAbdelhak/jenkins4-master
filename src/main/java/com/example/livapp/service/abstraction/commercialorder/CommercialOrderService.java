package com.example.livapp.service.abstraction.commercialorder;

import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialorder.CommercialOrderHolder;
import com.example.livapp.model.commercialorder.CommercialOrderStateHistory;
import com.example.livapp.model.commercialordernote.ExitCommercialOrderNote;
import com.example.livapp.model.types.OrderState;
import com.example.livapp.model.user.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommercialOrderService {

    Page<CommercialOrder> findPaginated(int pageNo, int pageSize);


    CommercialOrderHolder getCommercialOrderDetails(long id);


    CommercialOrder getCommercialOrder(long id);


    void updateCommercialOrderState(long commercialOrderId, OrderState state, User changedBy);


    void exitCommercialOrder(ExitCommercialOrderNote exitCommercialOrderNote);


    void cancelCommercialOrder(CommercialOrder commercialOrder);



    void archiveCommercialOrder(CommercialOrder commercialOrder);

    void archiveDeliveredCommercialOrders();

    int getUnprocessedCommercialOrders();

    List<CommercialOrderStateHistory> getCommercialOrderStateHistories(long commercialOrderId);
}
