package com.example.livapp.model.stock;

import com.example.livapp.model.supplier.Supplier;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Data
@Entity(name = "stock_entry")
public class StockEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(unique = true)
    private String entryNumber;

    @OneToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    private int totalQuantity;

    private double totalPrice;


    @Column(name = "entry_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date entryDate;


}
