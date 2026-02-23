package com.example.livapp.service.implementation.cash;

import com.example.livapp.model.cash.CashRegisterTransaction;
import com.example.livapp.model.customer.Customer;
import com.example.livapp.model.user.User;
import com.example.livapp.repository.cash.CashRegisterTransactionRepository;
import com.example.livapp.service.abstraction.cash.CashRegisterTransactionService;
import com.example.livapp.service.abstraction.customer.CustomerService;
import com.example.livapp.service.abstraction.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CashRegisterTransactionServiceImpl implements CashRegisterTransactionService {

    private final CashRegisterTransactionRepository cashRegisterTransactionRepository;
    private final CustomerService customerService;
    private final UserService userService;


    @Autowired
    public CashRegisterTransactionServiceImpl(CashRegisterTransactionRepository cashRegisterTransactionRepository, UserService userService, CustomerService customerService) {
        this.cashRegisterTransactionRepository = cashRegisterTransactionRepository;
        this.userService = userService;
        this.customerService = customerService;
    }


    @Override
    @Transactional
    public void addCashRegisterTransactionService(CashRegisterTransaction cashRegisterTransaction) {
        Customer customer = cashRegisterTransaction.getCustomer();
        User user = cashRegisterTransaction.getUser();
        if (customer != null) {
            customer = customerService.getCustomerById(customer.getId());
            customer.setTotalDebt(customer.getTotalDebt() - cashRegisterTransaction.getAmount());
            customerService.addOrUpdateCustomer(customer);
        } else {
            if (user != null) {
                user = userService.getUserById(user.getId());
                user.setCashAmount(user.getCashAmount() - cashRegisterTransaction.getAmount());
                userService.addOrUpdateUser(user);
            }
        }
        cashRegisterTransactionRepository.save(cashRegisterTransaction);

    }

    @Override
    public Page<CashRegisterTransaction> getCashRegisterTransactionsCustomer(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return cashRegisterTransactionRepository.findCashRegisterTransactionByCustomerNotNullAndUserIsNullOrderByIdDesc(pageable);

    }

    @Override
    public Page<CashRegisterTransaction> getCashRegisterTransactionsUser(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return cashRegisterTransactionRepository.findCashRegisterTransactionByUserNotNullAndCustomerIsNullOrderByIdDesc(pageable);

    }

    @Override
    public CashRegisterTransaction getCashRegisterTransactionById(long id) {
        return cashRegisterTransactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<CashRegisterTransaction> getTodayCashRegisterTransactions() {
        return cashRegisterTransactionRepository.findTodayCashTransactions();
    }

    @Override
    public double getTotalCashRegisterAmountToday() {
        return cashRegisterTransactionRepository.findTotalCashRegisterAmountToday();
    }
}
