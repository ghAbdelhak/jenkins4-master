package com.example.livapp.service.implementation.commercialorder;

import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialorder.CommercialOrderHolder;
import com.example.livapp.model.commercialorder.CommercialOrderLine;
import com.example.livapp.model.commercialorder.CommercialOrderStateHistory;
import com.example.livapp.model.commercialordernote.CommercialOrderNoteLine;
import com.example.livapp.model.commercialordernote.ExitCommercialOrderNote;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.types.OrderState;
import com.example.livapp.model.user.User;
import com.example.livapp.repository.commercialorder.CommercialOrderRepository;
import com.example.livapp.repository.commercialorder.CommercialOrderStateHistoryRepository;
import com.example.livapp.service.abstraction.commercialorder.CommercialOrderLinesService;
import com.example.livapp.service.abstraction.commercialorder.CommercialOrderService;
import com.example.livapp.service.abstraction.commercialordernote.CommercialOrderNoteService;
import com.example.livapp.service.abstraction.commercialstock.CommercialStockService;
import com.example.livapp.service.abstraction.pack.PackService;
import com.example.livapp.service.abstraction.product.ProductService;
import com.example.livapp.service.abstraction.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommercialOrderServiceImpl implements CommercialOrderService {

    private final CommercialOrderRepository commercialOrderRepository;
    private final CommercialOrderLinesService commercialOrderLinesService;
    private final CommercialOrderNoteService commercialOrderNoteService;
    private final CommercialOrderStateHistoryRepository commercialOrderStateHistoryRepository;
    private final ProductService productService;
    private final PackService packService;
    private final UserService userService;
    private final CommercialStockService commercialStockService;

    @Autowired
    public CommercialOrderServiceImpl(CommercialOrderRepository commercialOrderRepository, CommercialOrderLinesService commercialOrderLinesService, CommercialOrderStateHistoryRepository commercialOrderStateHistoryRepository, CommercialOrderNoteService commercialOrderNoteService, ProductService productService, PackService packService, UserService userService, CommercialStockService commercialStockService) {
        this.commercialOrderRepository = commercialOrderRepository;
        this.commercialOrderLinesService = commercialOrderLinesService;
        this.commercialOrderStateHistoryRepository = commercialOrderStateHistoryRepository;
        this.commercialOrderNoteService = commercialOrderNoteService;
        this.productService = productService;
        this.packService = packService;
        this.userService = userService;
        this.commercialStockService = commercialStockService;
    }

    @Override
    public Page<CommercialOrder> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return commercialOrderRepository.findAllByArchivedIsFalseOrderByIdDesc(pageable);
    }

    @Override
    public CommercialOrderHolder getCommercialOrderDetails(long id) {
        Optional<CommercialOrder> optional = commercialOrderRepository.findById(id);
        CommercialOrder commercialOrder = null;
        if (optional.isPresent()) {
            commercialOrder = optional.get();
        }
        List<CommercialOrderLine> commercialOrderLines = commercialOrderLinesService.getCommercialOrderLines(commercialOrder);
        CommercialOrderHolder commercialOrderHolder = new CommercialOrderHolder();
        commercialOrderHolder.setCommercialOrder(commercialOrder);
        commercialOrderHolder.setCommercialOrderLines(commercialOrderLines);
        return commercialOrderHolder;
    }

    @Override
    public CommercialOrder getCommercialOrder(long id) {
        Optional<CommercialOrder> optional = commercialOrderRepository.findById(id);
        CommercialOrder commercialOrder  = null;
        if (optional.isPresent()) {
            commercialOrder = optional.get();
        }
        return commercialOrder;
    }

    @Transactional
    @Override
    public void updateCommercialOrderState(long commercialOrderId, OrderState state, User changedBy) {
        CommercialOrder commercialOrder  = getCommercialOrder(commercialOrderId);
        CommercialOrderStateHistory commercialOrderStateHistory = new CommercialOrderStateHistory();
        commercialOrderStateHistory.setCommercialOrder(commercialOrder);
        commercialOrderStateHistory.setOldState(commercialOrder.getState());
        commercialOrderStateHistory.setNewState(state.getState());
        commercialOrderStateHistory.setChangedBy(changedBy);
        commercialOrder.setState(state.getState());
        // Update order state
        commercialOrderRepository.save(commercialOrder);
        commercialOrderStateHistoryRepository.save(commercialOrderStateHistory);

    }

    @Transactional
    @Override
    public void exitCommercialOrder(ExitCommercialOrderNote exitCommercialOrderNote) {
        // Add Exit Order Note
        User user = exitCommercialOrderNote.getDeliveryPerson();
        ExitCommercialOrderNote addedCommercialExitOrderNote = commercialOrderNoteService.addExitCommercialOrderNote(exitCommercialOrderNote);
        List<CommercialOrderNoteLine> providedOrderNoteLines = exitCommercialOrderNote.getCommercialOrderNoteLines();
        List<CommercialOrderNoteLine> commercialOrderNoteLines = providedOrderNoteLines.stream().filter(line -> line.getQuantity() > 0).collect(Collectors.toList());
        for (CommercialOrderNoteLine commercialOrderNoteLine : commercialOrderNoteLines) {
                if (commercialOrderNoteLine.getProduct().getId() != 0) {
                    Product product = productService.getProduct(commercialOrderNoteLine.getProduct().getId());
                    // Update real product quantity
                    int productSelledQuantity = commercialOrderNoteLine.getQuantity();
                    product.setQuantity(product.getQuantity() - productSelledQuantity);
                    commercialOrderNoteLine.setPack(null);
                    commercialOrderNoteLine.setProduct(product);
                    productService.updateProduct(product);
                    // Update Commercial Personal Stock
                    commercialStockService.updateCommercialStock(user, product, null, productSelledQuantity, OrderNoteType.EXIT);


                } else {
                    Pack pack = packService.getPack(commercialOrderNoteLine.getPack().getId());
                    // Update pack quantities and products
                    int packSelledQuantity = commercialOrderNoteLine.getQuantity();
                    packService.updatePackQte(pack, packSelledQuantity, OrderNoteType.EXIT);
                    commercialOrderNoteLine.setProduct(null);
                    commercialOrderNoteLine.setPack(pack);
                    // Update Commercial Personal Stock
                    commercialStockService.updateCommercialStock(user, null, pack, packSelledQuantity, OrderNoteType.EXIT);

                }
                commercialOrderNoteLine.setExitCommercialOrderNote(addedCommercialExitOrderNote);
                // Add Order Note Lines
                commercialOrderNoteService.addCommercialOrderNoteLine(commercialOrderNoteLine);

        }
        // Update Order State
        // Add Printed Exit Order
        updateCommercialOrderState(exitCommercialOrderNote.getCommercialOrder().getId(), OrderState.DELIVERED, exitCommercialOrderNote.getPreparedBy());
    }

    @Override
    @Transactional
    public void cancelCommercialOrder(CommercialOrder commercialOrder) {
        updateCommercialOrderState(commercialOrder.getId(), OrderState.CANCELLED, userService.getConnectedUser());
    }

    @Override
    public void archiveCommercialOrder(CommercialOrder commercialOrder) {
        commercialOrder.setArchived(true);
        commercialOrderRepository.save(commercialOrder);
    }

    @Override
    @Transactional
    public void archiveDeliveredCommercialOrders() {
        List<CommercialOrder> commercialOrders =  commercialOrderRepository.findAllByStateIsAndArchived(OrderState.DELIVERED.getState(), false);
        for (CommercialOrder commercialOrder : commercialOrders) {
            archiveCommercialOrder(commercialOrder);
        }
    }

    @Override
    public int getUnprocessedCommercialOrders() {
        List<String> states = new ArrayList<>(Arrays.asList("Pending","Preparing"));
        return commercialOrderRepository.countAllByStateIn(states);

    }

    @Override
    public List<CommercialOrderStateHistory> getCommercialOrderStateHistories(long commercialOrderId) {
        CommercialOrder commercialOrder = getCommercialOrder(commercialOrderId);
        return commercialOrderStateHistoryRepository.findAllByCommercialOrder(commercialOrder);
    }
}
