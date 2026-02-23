package com.example.livapp.repository.cash;

import com.example.livapp.model.cash.CashClosure;
import com.example.livapp.model.cash.CashRegisterTransaction;
import com.example.livapp.model.cash.WithdrawalTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashClosureRepository extends JpaRepository<CashClosure,Long> {

    Page<CashClosure> findCashClosureByOrderByIdDesc(Pageable pageable);


    @Query("SELECT count(*) FROM cash_closure t WHERE DATE(t.closureDate) = CURRENT_DATE")
    int isTodayCashClosed();
}
