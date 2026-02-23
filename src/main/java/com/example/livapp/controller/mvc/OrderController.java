package com.example.livapp.controller.mvc;


import com.example.livapp.model.order.OrderStateHistory;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.user.User;
import com.example.livapp.model.order.Order;
import com.example.livapp.model.order.OrderHolder;
import com.example.livapp.model.types.OrderState;
import com.example.livapp.model.ordernote.ExitOrderNote;
import com.example.livapp.model.ordernote.OrderNoteLine;
import com.example.livapp.service.abstraction.order.OrderService;
import com.example.livapp.service.abstraction.ordernote.OrderNoteService;
import com.example.livapp.service.abstraction.user.UserService;
import com.example.livapp.util.Constants;
import com.example.livapp.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    private final OrderNoteService orderNoteService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, OrderNoteService orderNoteService) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderNoteService = orderNoteService;
    }


    // Order List
    @GetMapping("/page/{pageNo}")
    public String findOrdersPaginated(@PathVariable(value = "pageNo") Integer pageNo, Model model,Long orderId) {
        int pageSize = Constants.pageSize;
        List<Order> orders = new ArrayList<>();
        if (orderId==null) {
            Page<Order> page = orderService.findPaginated(pageNo, pageSize);
            orders = page.getContent();
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
        }
        else {
            Order order = orderService.getOrder(orderId);
            if(order!=null) {
              orders.add(order);
            }

        }
        model.addAttribute("orders", orders);
        return "orders_list";
    }

    @GetMapping("/list")
    public String viewOrderList(Model model,@RequestParam(value = "orderId", required = false) Long orderId) {
        return findOrdersPaginated(1, model,orderId);
    }


    @GetMapping("/showOrderDetails/{id}")
    public String showOrderDetails(@PathVariable(value = "id") long id, Model model) {
        OrderHolder orderHolder = orderService.getOrderDetails(id);
        model.addAttribute("orderHolder", orderHolder);
        return "order_details";
    }

    @GetMapping("/showExitOrderForm/{id}")
    public String showExitOrderForm(@PathVariable(value = "id") long id, Model model) {
        OrderHolder orderHolder = orderService.getOrderDetails(id);
        Order order = orderHolder.getOrder();
        User preparedBy = userService.getConnectedUser();
        if (!order.getState().equals(OrderState.PENDING.getState())
                && !order.getState().equals(OrderState.PREPARING.getState())) {
            return null;
        }
        if(order.getState().equals(OrderState.PENDING.getState())) {
            orderService.updateOrderState(id,OrderState.PREPARING,preparedBy,null);
        }
        ExitOrderNote exitOrderNote = new ExitOrderNote();
        exitOrderNote.setOrder(order);
        exitOrderNote.setTotalPrice(order.getTotalPrice());
        List<OrderNoteLine> orderNoteLinesList = ListUtil.createOrderNoteLines(orderHolder.getOrderLines(), exitOrderNote);
        exitOrderNote.setOrderNoteLines(orderNoteLinesList);
        List<User> users = userService.getUsers();
        exitOrderNote.setPreparedBy(preparedBy);
        model.addAttribute("exitOrderNote", exitOrderNote);
        model.addAttribute("users", users);
        return "new_exit_order";
    }


    @PostMapping("/existOrder")
    public String exitOrder(@Valid ExitOrderNote exitOrderNote, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_exit_order";
        }
        orderService.exitOrder(exitOrderNote);
        return "redirect:/order/list";
    }


    @GetMapping("/showExitOrderDetails/{id}")
    public String showExitOrderDetails(@PathVariable(value = "id") long id, Model model) {
        ExitOrderNote exitOrderNote = (ExitOrderNote) orderNoteService.getOrderNote(id, OrderNoteType.EXIT);
        model.addAttribute("exitOrderNote", exitOrderNote);
        return "exit_order_details";
    }


    @GetMapping("/archiveOrder/{id}")
    public String archiveOrder(@PathVariable(value = "id") long id) {
        Order order = orderService.getOrder(id);
        if (order.getState().equals(OrderState.DELIVERED.getState())) {
            orderService.archiveOrder(order);
            return "redirect:/order/list";
        }
        return null;

    }

    @GetMapping("/archiveOrders")
    public String archiveOrders() {
        orderService.archiveDeliveredOrders();
        return "redirect:/order/list";
    }

    @GetMapping("/cancelOrder/{id}")
    public String cancelOrder(@PathVariable(value = "id") long id) {
        Order order = orderService.getOrder(id);
        if (order.getState().equals(OrderState.PENDING.getState()) || order.getState().equals(OrderState.PREPARING.getState())) {
            orderService.cancelOrder(order);
            return "redirect:/order/list";
        }
        return null;

    }


    @GetMapping("/showOrderHistory/{id}")
    public String showOrderHistory(@PathVariable(value = "id") long id, Model model) {
        List<OrderStateHistory> orderStateHistories = orderService.getOrderStateHistories(id);
        model.addAttribute("orderStateHistories", orderStateHistories);
        return "order_history";
    }


}
