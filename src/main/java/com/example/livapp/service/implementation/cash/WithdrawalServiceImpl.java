package com.example.livapp.service.implementation.cash;

import com.example.livapp.model.cash.CashClosure;
import com.example.livapp.model.cash.WithdrawalTransaction;
import com.example.livapp.repository.cash.CashClosureRepository;
import com.example.livapp.repository.cash.WithdrawalTransactionRepository;
import com.example.livapp.service.abstraction.cash.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private final WithdrawalTransactionRepository withdrawalTransactionRepository;

    @Autowired
    public WithdrawalServiceImpl(WithdrawalTransactionRepository withdrawalTransactionRepository) {
        this.withdrawalTransactionRepository = withdrawalTransactionRepository;
    }

    @Override
    public void addWithdrawalTransaction(WithdrawalTransaction withdrawalTransaction) {
        withdrawalTransactionRepository.save(withdrawalTransaction);
    }

    @Override
    public Page<WithdrawalTransaction> getWithdrawalTransactions(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return withdrawalTransactionRepository.findWithdrawalTransactionByOrderByIdDesc(pageable);
    }

    @Override
    public List<WithdrawalTransaction> getTodayWithdrawalTransactions() {
        return withdrawalTransactionRepository.findTodayWithdrawalTransaction();
    }

    @Override
    public double getTotalWithdrawalAmountToday() {
        return withdrawalTransactionRepository.findTotalWithdrawalAmountToday();
    }


}
