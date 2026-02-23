package com.example.livapp.model.ordernote;

import com.example.livapp.model.user.User;
import com.example.livapp.model.order.Order;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

// Bon de livraison
@Data
@Entity(name = "delivery_order_note")
public class DeliveryOrderNote extends OrderNote {

    @OneToOne
    @JoinColumn(name = "prepared_person_id")
    private User preparedBy;

    @OneToOne
    @JoinColumn(name = "delivery_person_id")
    private User deliveryPerson;

    @OneToMany(mappedBy = "deliveryOrderNote")
    private List<OrderNoteLine> orderNoteLines;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;



}
