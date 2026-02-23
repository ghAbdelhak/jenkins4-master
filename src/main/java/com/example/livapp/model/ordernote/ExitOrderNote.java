package com.example.livapp.model.ordernote;

import com.example.livapp.model.user.User;
import com.example.livapp.model.order.Order;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


// bon de sortie
@Data
@Entity(name = "exit_order_note")
public class ExitOrderNote extends OrderNote {


    @OneToOne
    @JoinColumn(name = "prepared_person_id")
    private User preparedBy;

    @OneToOne
    @JoinColumn(name = "delivery_person_id")
    private User deliveryPerson;

    @OneToMany(mappedBy = "exitOrderNote")
    private List<OrderNoteLine> orderNoteLines;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


}
