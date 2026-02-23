package com.example.livapp.controller.mvc;


import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialorder.CommercialOrderHolder;
import com.example.livapp.model.commercialorder.CommercialOrderStateHistory;
import com.example.livapp.model.commercialordernote.CommercialOrderNoteLine;
import com.example.livapp.model.commercialordernote.ExitCommercialOrderNote;
import com.example.livapp.model.types.OrderState;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.user.User;
import com.example.livapp.service.abstraction.commercialorder.CommercialOrderService;
import com.example.livapp.service.abstraction.commercialordernote.CommercialOrderNoteService;
import com.example.livapp.service.abstraction.user.UserService;
import com.example.livapp.util.Constants;
import com.example.livapp.util.ListUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commercial_order")
public class CommercialOrderController {

    private final CommercialOrderService commercialOrderService;
    private final UserService userService;
    private final CommercialOrderNoteService commercialOrderNoteService;

    @Autowired
    public CommercialOrderController(CommercialOrderService commercialOrderService, UserService userService, CommercialOrderNoteService commercialOrderNoteService) {
        this.commercialOrderService = commercialOrderService;
        this.userService = userService;
        this.commercialOrderNoteService = commercialOrderNoteService;
    }

    // Commercial Order List
    @GetMapping("/page/{pageNo}")
    public String findOrdersPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model,Long orderId) {
        int pageSize = Constants.pageSize;
        List<CommercialOrder> orders = new ArrayList<>();
        if (orderId==null) {
            Page<CommercialOrder> page = commercialOrderService.findPaginated(pageNo, pageSize);
            orders = page.getContent();
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());

        }
        else {
            CommercialOrder commercialOrder = commercialOrderService.getCommercialOrder(orderId);
            if(commercialOrder!=null) {
                orders.add(commercialOrder);
            }
        }

        model.addAttribute("orders", orders);
        return "commercial_orders_list";
    }

    @GetMapping("/list")
    public String viewCommercialOrders(Model model,@RequestParam(value = "orderId", required = false) Long orderId) {
        return findOrdersPaginated(1, model,orderId);
    }


    @GetMapping("/showOrderDetails/{id}")
    public String showOrderDetails(@PathVariable(value = "id") long id, Model model) {
        CommercialOrderHolder commercialOrderHolder = commercialOrderService.getCommercialOrderDetails(id);
        model.addAttribute("orderHolder", commercialOrderHolder);
        return "commercial_order_details";
    }


    @GetMapping("/showExitOrderForm/{id}")
    public String showExitOrderForm(@PathVariable(value = "id") long id, Model model) {
        CommercialOrderHolder commercialOrderHolder = commercialOrderService.getCommercialOrderDetails(id);
        CommercialOrder commercialOrder = commercialOrderHolder.getCommercialOrder();
        User user = userService.getUserById(commercialOrder.getUser().getId());
        User preparedBy = userService.getConnectedUser();
        if (!commercialOrder.getState().equals(OrderState.PENDING.getState())
                && !commercialOrder.getState().equals(OrderState.PREPARING.getState())) {
            return null;
        }
        if(commercialOrder.getState().equals(OrderState.PENDING.getState())) {
            commercialOrderService.updateCommercialOrderState(id,OrderState.PREPARING,preparedBy);
        }
        ExitCommercialOrderNote exitCommercialOrderNote = new ExitCommercialOrderNote();
        exitCommercialOrderNote.setDeliveryPerson(user);
        exitCommercialOrderNote.setCommercialOrder(commercialOrder);
        exitCommercialOrderNote.setTotalPrice(commercialOrder.getTotalPrice());
        List<CommercialOrderNoteLine> commercialOrderNoteLines = ListUtil.createCommercialOrderNoteLines(commercialOrderHolder.getCommercialOrderLines(), exitCommercialOrderNote);
        exitCommercialOrderNote.setCommercialOrderNoteLines(commercialOrderNoteLines);
        exitCommercialOrderNote.setPreparedBy(preparedBy);
        model.addAttribute("exitCommercialOrderNote", exitCommercialOrderNote);
        model.addAttribute("user", user);

        return "new_exit_commercial_order";
    }


    @PostMapping("/existOrder")
    public String exitOrder(@Valid ExitCommercialOrderNote exitCommercialOrderNote, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_exit_commercial_order";
        }
        commercialOrderService.exitCommercialOrder(exitCommercialOrderNote);
        return "redirect:/commercial_order/list";
    }


    @GetMapping("/showExitOrderDetails/{id}")
    public String showExitOrderDetails(@PathVariable(value = "id") long id, Model model) {
        ExitCommercialOrderNote exitCommercialOrderNote = (ExitCommercialOrderNote) commercialOrderNoteService.getCommercialOrderNote(id, OrderNoteType.EXIT);
        model.addAttribute("exitCommercialOrderNote", exitCommercialOrderNote);
        return "exit_commercial_order_details";
    }


    @GetMapping("/showOrderHistory/{id}")
    public String showOrderHistory(@PathVariable(value = "id") long id, Model model) {
        List<CommercialOrderStateHistory> commercialOrderStateHistories = commercialOrderService.getCommercialOrderStateHistories(id);
        model.addAttribute("commercialOrderStateHistories", commercialOrderStateHistories);
        return "commercial_order_history";
    }


    @GetMapping("/cancelOrder/{id}")
    public String cancelOrder(@PathVariable(value = "id") long id) {
        CommercialOrder commercialOrder  = commercialOrderService.getCommercialOrder(id);
        if (commercialOrder.getState().equals(OrderState.PENDING.getState()) || commercialOrder.getState().equals(OrderState.PREPARING.getState())) {
            commercialOrderService.cancelCommercialOrder(commercialOrder);
            return "redirect:/commercial_order/list";
        }
        return null;

    }



    @GetMapping("/archiveOrder/{id}")
    public String archiveOrder(@PathVariable(value = "id") long id) {
        CommercialOrder commercialOrder = commercialOrderService.getCommercialOrder(id);
        if (commercialOrder.getState().equals(OrderState.DELIVERED.getState())) {
            commercialOrderService.archiveCommercialOrder(commercialOrder);
            return "redirect:/commercial_order/list";
        }
        return null;

    }

    @GetMapping("/archiveOrders")
    public String archiveOrders() {
        commercialOrderService.archiveDeliveredCommercialOrders();
        return "redirect:/commercial_order/list";
    }


}
