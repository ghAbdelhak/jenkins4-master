package com.example.livapp.model.pack;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity(name = "pack_history")
public class PackHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name = "pack_id", nullable = false)
    private Pack pack;

    private int quantity;
    private String changedBy = "";
    private double price;
    private boolean state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date changeDate;


}
