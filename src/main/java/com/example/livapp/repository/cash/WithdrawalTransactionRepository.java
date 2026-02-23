package com.example.livapp.repository.cash;

import com.example.livapp.model.cash.CashRegisterTransaction;
import com.example.livapp.model.cash.WithdrawalTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WithdrawalTransactionRepository extends JpaRepository<WithdrawalTransaction, Long> {

    Page<WithdrawalTransaction> findWithdrawalTransactionByOrderByIdDesc(Pageable pageable);

    @Query("SELECT t FROM withdrawal_transactions t WHERE DATE(t.withdrawalDate) = CURRENT_DATE")
    List<WithdrawalTransaction> findTodayWithdrawalTransaction();


    @Query("SELECT sum(amount) as amount FROM withdrawal_transactions t WHERE DATE(t.withdrawalDate) = CURRENT_DATE")
    double findTotalWithdrawalAmountToday();

}
