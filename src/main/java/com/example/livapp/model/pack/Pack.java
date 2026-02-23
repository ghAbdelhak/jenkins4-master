package com.example.livapp.model.pack;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "packs")
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(unique = true)
    private String name;

    private int quantity;
    private double price = 0.0;
    private boolean state = true;
    private int reservedOrders = 0;


}
