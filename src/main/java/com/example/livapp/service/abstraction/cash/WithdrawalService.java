package com.example.livapp.service.abstraction.cash;


import com.example.livapp.model.cash.CashClosure;
import com.example.livapp.model.cash.WithdrawalTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WithdrawalService {

    void addWithdrawalTransaction(WithdrawalTransaction withdrawalTransaction);

    Page<WithdrawalTransaction> getWithdrawalTransactions(int pageNo, int pageSize);


    List<WithdrawalTransaction> getTodayWithdrawalTransactions();

    double getTotalWithdrawalAmountToday();

}
