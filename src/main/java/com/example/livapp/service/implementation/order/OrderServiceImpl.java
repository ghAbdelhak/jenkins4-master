package com.example.livapp.service.implementation.order;

import com.example.livapp.model.types.OrderState;
import com.example.livapp.model.user.User;
import com.example.livapp.model.order.*;
import com.example.livapp.model.ordernote.ExitOrderNote;
import com.example.livapp.model.ordernote.OrderNoteLine;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;
import com.example.livapp.repository.order.OrderRepository;
import com.example.livapp.repository.order.OrderStateHistoryRepository;
import com.example.livapp.service.abstraction.order.OrderLinesService;
import com.example.livapp.service.abstraction.order.OrderService;
import com.example.livapp.service.abstraction.ordernote.OrderNoteService;
import com.example.livapp.service.abstraction.pack.PackService;
import com.example.livapp.service.abstraction.product.ProductService;
import com.example.livapp.service.abstraction.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final OrderStateHistoryRepository orderStateHistoryRepository;

    private final OrderLinesService orderLinesService;


    private final ProductService productService;

    private final PackService packService;

    private final OrderNoteService orderNoteService;

    private final UserService userService;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderStateHistoryRepository orderStateHistoryRepository, OrderLinesService orderLinesService , ProductService productService, PackService packService, OrderNoteService orderNoteService, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderStateHistoryRepository = orderStateHistoryRepository;
        this.orderLinesService = orderLinesService;
        this.productService = productService;
        this.packService = packService;
        this.orderNoteService = orderNoteService;
        this.userService = userService;
    }


    @Override
    public Page<Order> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return orderRepository.findAllByArchivedIsFalseOrderByIdDesc(pageable);

    }

    @Override
    public OrderHolder getOrderDetails(long id) {
        Optional<Order> optional = orderRepository.findById(id);
        Order order = null;
        if (optional.isPresent()) {
            order = optional.get();
        }
        List<OrderLine> orderLineList = orderLinesService.getOrderLines(order);
        OrderHolder orderHolder = new OrderHolder();
        orderHolder.setOrder(order);
        orderHolder.setOrderLines(orderLineList);
        return orderHolder;
    }




    @Override
    public Order getOrder(long id) {
       return orderRepository.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public void updateOrderState(long orderId, OrderState state, User changedBy, User deliveryPerson) {
        Order order = getOrder(orderId);
        OrderStateHistory orderStateHistory = new OrderStateHistory();
        orderStateHistory.setOrder(order);
        orderStateHistory.setOldState(order.getState());
        orderStateHistory.setNewState(state.getState());
        orderStateHistory.setChangedBy(changedBy);
        orderStateHistory.setDeliveryPerson(deliveryPerson);
        order.setState(state.getState());
        // Update order state
        orderRepository.save(order);
        orderStateHistoryRepository.save(orderStateHistory);

    }


    @Transactional
    @Override
    public void updateReservedOrderQuantities(long orderId, OrderNoteType orderNoteType) {
        // Update order reserved quantity
        List<OrderLine> orderLines = getOrderDetails(orderId).getOrderLines();
        for (OrderLine orderLine : orderLines) {
            if (orderLine.getProduct() != null) {
                Product product = productService.getProduct(orderLine.getProduct().getId());
                int reservedOrderQuantity = orderLine.getQuantity();
                if(orderNoteType.getType().equals(OrderNoteType.EXIT.getType())) {
                    product.setReservedOrders(product.getReservedOrders() - reservedOrderQuantity);
                }
                productService.updateProduct(product);
            } else {
                Pack pack = packService.getPack(orderLine.getPack().getId());
                int packOrderQuantity = orderLine.getQuantity();
                // update pack reserved quantity order
                if(orderNoteType.getType().equals(OrderNoteType.EXIT.getType())) {
                    pack.setReservedOrders(pack.getReservedOrders() - packOrderQuantity);
                }
                    packService.updatePack(pack);
            }

        }

    }

    @Transactional
    @Override
    public void exitOrder(ExitOrderNote exitOrderNote) {
        // Add Exit Order Note
        ExitOrderNote addedExitOrderNote = orderNoteService.addExitOrderNote(exitOrderNote);
        long orderId = exitOrderNote.getOrder().getId();
        List<OrderNoteLine>  providedOrderNoteLines = exitOrderNote.getOrderNoteLines();
        // filter to take only quantities >0
        List<OrderNoteLine> orderNoteLinesList = providedOrderNoteLines.stream().filter(line -> line.getQuantity() > 0).collect(Collectors.toList());

        for (OrderNoteLine orderNoteLine : orderNoteLinesList) {
                if (orderNoteLine.getProduct().getId() != 0) {
                    Product product = productService.getProduct(orderNoteLine.getProduct().getId());
                    // Update real product quantity
                    product.setQuantity(product.getQuantity() - orderNoteLine.getQuantity());
                    orderNoteLine.setPack(null);
                    orderNoteLine.setProduct(product);
                    // update added
                    productService.updateProduct(product);
                }
                else {
                    Pack pack = packService.getPack(orderNoteLine.getPack().getId());
                    // Update pack quantities and products
                    int packSelledQuantity = orderNoteLine.getQuantity();
                    packService.updatePackQte(pack, packSelledQuantity, OrderNoteType.EXIT);
                    orderNoteLine.setProduct(null);
                    orderNoteLine.setPack(pack);
                }

            // update Reserved Quantities
            updateReservedOrderQuantities(orderId,OrderNoteType.EXIT);
            // Add Order Note Lines
                orderNoteLine.setExitOrderNote(addedExitOrderNote);
                orderNoteService.addOrderNoteLine(orderNoteLine);
        }
        // Update Order State
        updateOrderState(exitOrderNote.getOrder().getId(), OrderState.OUT_FOR_DELIVERY, exitOrderNote.getPreparedBy(), exitOrderNote.getDeliveryPerson());

    }

    @Override
    @Transactional
    public void cancelOrder(Order order) {
        List<OrderLine> orderLines = orderLinesService.getOrderLines(order);
        for (OrderLine orderLine : orderLines) {
            // Update products reserved Quantities
            if (orderLine.getProduct() != null) {
                Product product = productService.getProduct(orderLine.getProduct().getId());
                product.setReservedOrders(product.getReservedOrders() - orderLine.getQuantity());
                productService.updateProduct(product);
            }
            // Update packs reserved Quantities
            if (orderLine.getPack() != null) {
                Pack pack = packService.getPack(orderLine.getPack().getId());
                pack.setReservedOrders(pack.getReservedOrders() - orderLine.getQuantity());
                packService.updatePack(pack);
            }
        }
        // Update order state
        updateOrderState(order.getId(), OrderState.CANCELLED, userService.getConnectedUser(), null);
    }


    @Override
    public void archiveOrder(Order order) {
        order.setArchived(true);
        orderRepository.save(order);

    }


    @Transactional
    @Override
    public void archiveDeliveredOrders() {
        List<Order> orders =  orderRepository.findAllByStateIsAndArchived(OrderState.DELIVERED.getState(), false);
        for (Order order : orders) {
            archiveOrder(order);
        }
    }

    @Override
    public int getUnprocessedClientOrders() {
        List<String> states = new ArrayList<>(Arrays.asList("Pending","Preparing"));
        return orderRepository.countAllByStateIn(states);
    }


    @Override
    public List<OrderStateHistory> getOrderStateHistories(long orderId) {
        Order order = getOrder(orderId);
        return orderStateHistoryRepository.findAllByOrder(order);
    }

}
