package com.example.livapp.service.abstraction.ordernote;

import com.example.livapp.model.ordernote.ExitOrderNote;
import com.example.livapp.model.ordernote.OrderNote;
import com.example.livapp.model.ordernote.OrderNoteLine;
import com.example.livapp.model.types.OrderNoteType;

public interface OrderNoteService {


    OrderNote getOrderNote(long orderId, OrderNoteType orderNoteType);

    ExitOrderNote addExitOrderNote(ExitOrderNote exitOrderNote);

    void addOrderNoteLine(OrderNoteLine orderNoteLine);




}
