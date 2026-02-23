package com.example.livapp.controller.mvc;

import com.example.livapp.model.cash.CashRegisterTransaction;
import com.example.livapp.model.cash.WithdrawalTransaction;
import com.example.livapp.model.customer.Customer;
import com.example.livapp.model.user.User;
import com.example.livapp.service.abstraction.cash.CashRegisterTransactionService;
import com.example.livapp.service.abstraction.cash.WithdrawalService;
import com.example.livapp.service.abstraction.customer.CustomerService;
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
/** testing webhook **/
@Controller
@RequestMapping("/cash_register_transaction")
public class CashRegisterTransactionController {

    private final WithdrawalService withdrawalService;
    private final UserService userService;
    private final CustomerService customerService;
    private final CashRegisterTransactionService cashRegisterTransactionService;


    @Autowired
    public CashRegisterTransactionController(CashRegisterTransactionService cashRegisterTransactionService, UserService userService, CustomerService customerService, WithdrawalService withdrawalService) {
        this.cashRegisterTransactionService = cashRegisterTransactionService;
        this.userService = userService;
        this.customerService = customerService;
        this.withdrawalService = withdrawalService;
    }


    @GetMapping("/customer/page/{pageNo}")
    public String findCashRegisterTransactionCustomersPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model) {
        int pageSize = Constants.pageSize;
        List<CashRegisterTransaction> cashRegisterTransactions = new ArrayList<>();
        Page<CashRegisterTransaction> page = cashRegisterTransactionService.getCashRegisterTransactionsCustomer(pageNo, pageSize);
        if (page != null) {
            cashRegisterTransactions = page.getContent();
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("cashRegisterTransactions", cashRegisterTransactions);
        return "cash_customers_list";
    }

    @GetMapping("/customer/list")
    public String viewCashCustomersPage(Model model) {
        return findCashRegisterTransactionCustomersPaginated(1, model);
    }


    @GetMapping("/user/page/{pageNo}")
    public String findCashRegisterTransactionUsersPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model) {
        int pageSize = Constants.pageSize;
        List<CashRegisterTransaction> cashRegisterTransactions = new ArrayList<>();
        Page<CashRegisterTransaction> page = cashRegisterTransactionService.getCashRegisterTransactionsUser(pageNo, pageSize);
        if (page != null) {
            cashRegisterTransactions = page.getContent();
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("cashRegisterTransactions", cashRegisterTransactions);
        return "cash_users_list";
    }

    @GetMapping("/user/list")
    public String viewCashUsersPage(Model model) {
        return findCashRegisterTransactionUsersPaginated(1, model);
    }


    @GetMapping("/user/showNewPaymentForm")
    public String showNewPaymentUserForm(Model model) {
        CashRegisterTransaction cashRegisterTransaction = new CashRegisterTransaction();
        List<User> commercials = userService.getUsersWithCash();
        User cashier = userService.getConnectedUser();
        cashRegisterTransaction.setCashier(cashier);
        model.addAttribute("commercials", commercials);
        model.addAttribute("cashRegisterTransaction", cashRegisterTransaction);
        return "new_user_payment";
    }


    @GetMapping("/customer/showNewPaymentForm")
    public String showNewPaymentCustomerForm(Model model) {
        CashRegisterTransaction cashRegisterTransaction = new CashRegisterTransaction();
        List<Customer> customers = customerService.getCustomersWithDebt();
        User cashier = userService.getConnectedUser();
        cashRegisterTransaction.setCashier(cashier);
        model.addAttribute("customers", customers);
        model.addAttribute("cashRegisterTransaction", cashRegisterTransaction);
        return "new_customer_payment";
    }



    @PostMapping("/user/saveTransaction")
    public String saveUserTransaction(@Valid CashRegisterTransaction cashRegisterTransaction, BindingResult bindingResult) {
        long userId = cashRegisterTransaction.getUser().getId();
        User user = userService.getUserById(userId);
        if(user.getCashAmount()<cashRegisterTransaction.getAmount()) {
            return "redirect:/cash_register_transaction/user/showNewPaymentForm?messageCode=0&cash="+user.getCashAmount();

        }

        cashRegisterTransactionService.addCashRegisterTransactionService(cashRegisterTransaction);
        return "redirect:/cash_register_transaction/user/list";
    }


    @PostMapping("/customer/saveTransaction")
    public String saveCustomerTransaction(@Valid CashRegisterTransaction cashRegisterTransaction, BindingResult bindingResult) {
        long customerId = cashRegisterTransaction.getCustomer().getId();
        Customer customer = customerService.getCustomerById(customerId);
        if(customer.getTotalDebt()<cashRegisterTransaction.getAmount()) {
            return "redirect:/cash_register_transaction/customer/showNewPaymentForm?messageCode=0&cash="+customer.getTotalDebt();

        }
        cashRegisterTransactionService.addCashRegisterTransactionService(cashRegisterTransaction);
        return "redirect:/cash_register_transaction/customer/list";
    }



    @GetMapping("/showPaymentDetails/{id}")
    public String showPaymentDetails(@PathVariable(value = "id") long id, Model model) {
        User user = null;
        Customer customer = null;
        CashRegisterTransaction cashRegisterTransaction = cashRegisterTransactionService.getCashRegisterTransactionById(id);
        if(cashRegisterTransaction.getUser() != null) {
            user = userService.getUserById(cashRegisterTransaction.getUser().getId());

        }
        if(cashRegisterTransaction.getCustomer() != null) {
            customer = customerService.getCustomerById(cashRegisterTransaction.getCustomer().getId());
        }

        User cashier = userService.getUserById(cashRegisterTransaction.getCashier().getId());

        model.addAttribute("transaction", cashRegisterTransaction);
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        model.addAttribute("cashier", cashier);
        return "payment_details";
    }




    @GetMapping("/showTodayTransactions")
    public String showTodayTransactions(Model model) {
        List<CashRegisterTransaction> cashRegisterTransactions = cashRegisterTransactionService.getTodayCashRegisterTransactions();
        List<WithdrawalTransaction> withdrawalTransactions = withdrawalService.getTodayWithdrawalTransactions();
        model.addAttribute("cashRegisterTransactions", cashRegisterTransactions);
        model.addAttribute("withdrawalTransactions", withdrawalTransactions);
        return "today_transactions";
    }









}
