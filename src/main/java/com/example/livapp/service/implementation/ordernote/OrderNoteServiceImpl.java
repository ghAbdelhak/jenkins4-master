package com.example.livapp.service.implementation.ordernote;

import com.example.livapp.model.ordernote.*;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.repository.ordernote.*;
import com.example.livapp.service.abstraction.ordernote.OrderNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderNoteServiceImpl implements OrderNoteService {


    private final ExitOrderNoteRepository exitOrderNoteRepository;
    private final OrderNoteLinesRepository orderNoteLinesRepository;

    @Autowired
    public OrderNoteServiceImpl(ExitOrderNoteRepository exitOrderNoteRepository
            , OrderNoteLinesRepository orderNoteLinesRepository) {
        this.exitOrderNoteRepository = exitOrderNoteRepository;
        this.orderNoteLinesRepository = orderNoteLinesRepository;
    }

/*
    @Override
    public OrderNote getOrderNote(long orderId, OrderNoteType orderNoteType) {
        OrderNote orderNote = null;
        switch (orderNoteType) {
            case EXIT: {
                Optional<ExitOrderNote> optional = exitOrderNoteRepository.findById(orderId);
                if (optional.isPresent()) {
                    orderNote = optional.get();
                }
                break;
            }

            case DELIVERY: {
                Optional<DeliveryOrderNote> optional = deliveryOrderNoteRepository.findById(orderId);
                if (optional.isPresent()) {
                    orderNote = optional.get();
                }
                break;
            }

            case PURCHASE: {
                Optional<PurchaseOrderNote> optional = purchaseOrderNoteRepository.findById(orderId);
                if (optional.isPresent()) {
                    orderNote = optional.get();
                }
                break;
            }

        }
        return orderNote;
    }
*/

    @Override
    public OrderNote getOrderNote (long orderId, OrderNoteType orderNoteType) {
        OrderNote orderNote = null;
        switch (orderNoteType) {
            case EXIT: {
                orderNote = exitOrderNoteRepository.findByOrderId(orderId);
                break;
            }

        }
        return orderNote;

    }



    @Override
    public ExitOrderNote addExitOrderNote(ExitOrderNote exitOrderNote) {
        return exitOrderNoteRepository.save(exitOrderNote);
    }

    @Override
    public void addOrderNoteLine(OrderNoteLine orderNoteLine) {
        orderNoteLinesRepository.save(orderNoteLine);
    }






}
