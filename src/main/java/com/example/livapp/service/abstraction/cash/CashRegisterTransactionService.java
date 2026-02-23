package com.example.livapp.service.abstraction.cash;

import com.example.livapp.model.cash.CashRegisterTransaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CashRegisterTransactionService {

    void addCashRegisterTransactionService(CashRegisterTransaction cashRegisterTransaction);

    Page<CashRegisterTransaction> getCashRegisterTransactionsCustomer(int pageNo, int pageSize);


    Page<CashRegisterTransaction> getCashRegisterTransactionsUser(int pageNo, int pageSize);

    CashRegisterTransaction getCashRegisterTransactionById(long id);

    List<CashRegisterTransaction> getTodayCashRegisterTransactions();

    double getTotalCashRegisterAmountToday();

}
