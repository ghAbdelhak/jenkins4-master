package com.example.livapp.controller.mvc;


import com.example.livapp.service.abstraction.commercialorder.CommercialOrderService;
import com.example.livapp.service.abstraction.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final OrderService orderService;
    private final CommercialOrderService commercialOrderService;

    @Autowired
    public MainController(OrderService orderService, CommercialOrderService commercialOrderService) {
        this.orderService = orderService;
        this.commercialOrderService = commercialOrderService;
    }


    @GetMapping("/")
    public String home(Model model) {
        int unprocessedCommercialOrders = commercialOrderService.getUnprocessedCommercialOrders();
        int unprocessedClientOrders = orderService.getUnprocessedClientOrders();
        model.addAttribute("unprocessedClientOrders", unprocessedClientOrders);
        model.addAttribute("unprocessedCommercialOrders", unprocessedCommercialOrders);
        model.addAttribute("unprocessedOrders",unprocessedCommercialOrders+unprocessedClientOrders);

        // Add other notification counts...
        return "index";
    }
}
