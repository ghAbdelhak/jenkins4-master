package com.example.livapp.repository.cash;

import com.example.livapp.model.cash.CashRegisterTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashRegisterTransactionRepository extends JpaRepository<CashRegisterTransaction, Long> {


    Page<CashRegisterTransaction> findCashRegisterTransactionByCustomerNotNullAndUserIsNullOrderByIdDesc(Pageable pageable);

    Page<CashRegisterTransaction> findCashRegisterTransactionByUserNotNullAndCustomerIsNullOrderByIdDesc(Pageable pageable);


    @Query("SELECT t FROM cash_register_transactions t WHERE DATE(t.paymentDate) = CURRENT_DATE")
    List<CashRegisterTransaction> findTodayCashTransactions();


    @Query("SELECT sum(amount) as amount FROM cash_register_transactions t WHERE DATE(t.paymentDate) = CURRENT_DATE")

    double findTotalCashRegisterAmountToday();


}
