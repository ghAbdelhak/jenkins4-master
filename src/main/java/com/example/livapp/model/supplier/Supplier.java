package com.example.livapp.model.supplier;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    @NotBlank
    private String name;

    @Column
    @NotNull
    @NotBlank
    private String address;

    @Column
    @NotNull
    @NotBlank
    @Pattern(regexp = "^0\\d{9}$",
            message = "Invalid phone number format")
    private String mobilePhone;

    @Column
    @NotNull
    @NotBlank
    @Pattern(regexp = "^0\\d{8}$",
            message = "Invalid phone number format")
    private String homePhone;

    @Column
    @NotNull
    @NotBlank
    @Email
    private String email;


    private boolean state = true;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date registrationDate;

}
