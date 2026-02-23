package com.example.livapp.repository.customer;

import com.example.livapp.model.customer.Customer;
import com.example.livapp.model.order.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByTotalDebtIsGreaterThan(Double totalDebt);

    @NotNull Page<Customer> findAll(@NotNull Pageable pageable);

}
