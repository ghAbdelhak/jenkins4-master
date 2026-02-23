package com.example.livapp.controller.mvc;


import com.example.livapp.model.cash.CashClosure;
import com.example.livapp.model.user.User;
import com.example.livapp.service.abstraction.cash.CashClosureService;
import com.example.livapp.service.abstraction.cash.CashRegisterTransactionService;
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


/* add some comments to explain the code */

@Controller
@RequestMapping("/cash_closure")
public class CashClosureController {

    private final CashClosureService cashClosureService;
    private final UserService userService;
    private final CashRegisterTransactionService cashRegisterTransactionService;
    private final WithdrawalService withdrawalService;

    @Autowired
    public CashClosureController(CashClosureService cashClosureService,UserService userService,CashRegisterTransactionService cashRegisterTransactionService, WithdrawalService withdrawalService) {
        this.cashClosureService = cashClosureService;
        this.userService = userService;
        this.cashRegisterTransactionService = cashRegisterTransactionService;
        this.withdrawalService = withdrawalService;
    }


    // Cash Closure

    @GetMapping("/list")
    public String viewCashClosurePage(Model model) {
        return findCashClosuresPaginated(1, model);
    }


    @GetMapping("/page/{pageNo}")
    public String findCashClosuresPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model) {
        int pageSize = Constants.pageSize;
        List<CashClosure> cashClosures = new ArrayList<>();
        Page<CashClosure> page = cashClosureService.getCashClosures(pageNo, pageSize);
        boolean isCashClosed = cashClosureService.isTodayCashClosed();
        if (page != null) {
            cashClosures = page.getContent();
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("cashClosures", cashClosures);
        model.addAttribute("isCashClosed", isCashClosed);
        return "cash_closure_list";
    }


    @GetMapping("/showNewClosureForm")
    public String showClosureForm(Model model) {
        boolean isCashClosed = cashClosureService.isTodayCashClosed();
        if (isCashClosed) {
            return "redirect:/cash_closure/list";

        }
        double amountReceived = cashRegisterTransactionService.getTotalCashRegisterAmountToday();
        double amountWithdrawn = withdrawalService.getTotalWithdrawalAmountToday();
        User cashier = userService.getConnectedUser();
        CashClosure cashClosure = new CashClosure();
        cashClosure.setAmountReceived(amountReceived);
        cashClosure.setAmountWithdrawn(amountWithdrawn);
        cashClosure.setCashier(cashier);
        model.addAttribute("cashClosure", cashClosure);
        return "new_cash_closure";
    }




    @PostMapping("/saveClosure")
    public String saveCashClosure(@Valid CashClosure cashClosure, BindingResult bindingResult) {
        boolean isCashClosed = cashClosureService.isTodayCashClosed();
        if (isCashClosed) {
            return "redirect:/cash_closure/list";

        }
        cashClosureService.addCashClosure(cashClosure);
        return "redirect:/cash_closure/list";

    }



    @GetMapping("/showCashClosureDetails/{id}")
    public String showPaymentDetails(@PathVariable(value = "id") long id, Model model) {
        CashClosure cashClosure = cashClosureService.getCashClosureById(id);
        model.addAttribute("cashClosure", cashClosure);
        return "cash_closure_details";
    }

}
