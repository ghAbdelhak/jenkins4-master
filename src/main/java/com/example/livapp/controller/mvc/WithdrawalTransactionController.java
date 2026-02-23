package com.example.livapp.controller.mvc;


import com.example.livapp.model.cash.WithdrawalTransaction;
import com.example.livapp.model.user.User;
import com.example.livapp.service.abstraction.cash.WithdrawalService;
import com.example.livapp.service.abstraction.user.UserService;
import com.example.livapp.util.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/withdrawal_transaction")
public class WithdrawalTransactionController {

    private final WithdrawalService withdrawalService;
    private final UserService userService;

    @Autowired
    public WithdrawalTransactionController(WithdrawalService withdrawalService, UserService userService) {
        this.withdrawalService = withdrawalService;
        this.userService = userService;
    }


    @GetMapping("/page/{pageNo}")
    public String findWithdrawalTransactionsPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model) {
        int pageSize = Constants.pageSize;
        List<WithdrawalTransaction> withdrawalTransactions = new ArrayList<>();
        Page<WithdrawalTransaction> page = withdrawalService.getWithdrawalTransactions(pageNo, pageSize);
        if (page != null) {
            withdrawalTransactions = page.getContent();
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("withdrawalTransactions", withdrawalTransactions);
        return "withdrawal_list";
    }

    @GetMapping("/list")
    public String viewWithdrawalTransactionsPage(Model model) {
        return findWithdrawalTransactionsPaginated(1, model);
    }



    @GetMapping("/showNewWithdrawalForm")
    public String showNewPaymentUserForm(Model model) {
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
        User cashier = userService.getConnectedUser();
        withdrawalTransaction.setCashier(cashier);
        model.addAttribute("withdrawalTransaction", withdrawalTransaction);
        return "new_withdrawal";
    }


    @PostMapping("/saveTransaction")
    public String saveWithdrawalTransaction(@Valid WithdrawalTransaction withdrawalTransaction, BindingResult bindingResult) {
        withdrawalService.addWithdrawalTransaction(withdrawalTransaction);
        return "redirect:/withdrawal_transaction/list";
    }





}

