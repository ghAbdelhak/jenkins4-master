package com.example.livapp.model.commercialordernote;


import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.ordernote.OrderNote;
import com.example.livapp.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "exit_commercial_order_note")
public class ExitCommercialOrderNote extends OrderNote {

    @OneToOne
    @JoinColumn(name = "prepared_person_id")
    private User preparedBy;

    @OneToOne
    @JoinColumn(name = "delivery_person_id")
    private User deliveryPerson;

    @OneToMany(mappedBy = "exitCommercialOrderNote")
    private List<CommercialOrderNoteLine> commercialOrderNoteLines;

    @OneToOne
    @JoinColumn(name = "commercial_order_id", nullable = false)
    private CommercialOrder commercialOrder;



}
