package com.example.livapp.model.customer;


import com.example.livapp.model.types.CustomerType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String wilaya;
    private String commune;
    private String ownerName;
    private Double latitude;
    private Double longitude;
    private Double totalRevenue =0.0;
    private Double totalDebt = 0.0;
    private String customerType = CustomerType.RETAILER.getType();




}
