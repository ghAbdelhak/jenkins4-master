/*
package com.example.livapp.service.implementation.print;

import com.example.livapp.model.order.Order;
import com.example.livapp.model.ordernote.ExitOrderNote;
import com.example.livapp.model.ordernote.OrderNote;
import com.example.livapp.model.ordernote.OrderNoteLine;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackProducts;
import com.example.livapp.model.print.PrintOrderNote;
import com.example.livapp.model.print.PrintOrderNoteLine;
import com.example.livapp.model.product.Product;
import com.example.livapp.repository.print.PrintOrderNoteLineRepository;
import com.example.livapp.repository.print.PrintOrderNoteRepository;
import com.example.livapp.service.abstraction.order.OrderService;
import com.example.livapp.service.abstraction.pack.PackProductsService;
import com.example.livapp.service.abstraction.pack.PackService;
import com.example.livapp.service.abstraction.print.PrintOrderNoteService;
import com.example.livapp.service.abstraction.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrintOrderNoteServiceImpl implements PrintOrderNoteService {

    private final PrintOrderNoteLineRepository printOrderNoteLineRepository;
    private final PrintOrderNoteRepository printOrderNoteRepository;
    private final ProductService productService;
    private final PackService packService;
    private final PackProductsService packProductsService;
    private final OrderService orderService;


    @Autowired
    public PrintOrderNoteServiceImpl(PrintOrderNoteLineRepository printOrderNoteLineRepository,ProductService productService,PackService packService,PackProductsService packProductsService,PrintOrderNoteRepository printOrderNoteRepository,
                                     OrderService orderService) {
        this.printOrderNoteLineRepository = printOrderNoteLineRepository;
        this.productService = productService;
        this.packService = packService;
        this.packProductsService = packProductsService;
        this.printOrderNoteRepository = printOrderNoteRepository;
        this.orderService = orderService;
    }

    @Override
    @Transactional
    public void addPrintOrderNote(OrderNote orderNote, OrderNoteType orderNoteType) {
        PrintOrderNote printOrderNote = new PrintOrderNote();
        ExitOrderNote exitOrderNote = null;
        List<OrderNoteLine> orderNoteLinesList = new ArrayList<>();
        long orderId = 0;
        Pack pack = null;
        if(orderNoteType.getType().equals(OrderNoteType.EXIT.getType())) {
            exitOrderNote = (ExitOrderNote) orderNote;
            orderId = exitOrderNote.getOrder().getId();
            orderNoteLinesList = exitOrderNote.getOrderNoteLines();
        }
        // Get order
        Order order = orderService.getOrder(orderId);
        // Calculate total prices (price * quantity for each line)
        double totalPrices = orderNoteLinesList.stream()
                .mapToDouble(line -> line.getPrice() * line.getQuantity())
                .sum();
        // setters for printOrderNote
        printOrderNote.setOrder(order);
        printOrderNote.setType(orderNoteType.getType());
        printOrderNote.setTotalPrice(totalPrices);
        // add PrintOrderNote
        PrintOrderNote addedPrintOrderNote = printOrderNoteRepository.save(printOrderNote);

        // Get Lines
        for (OrderNoteLine orderNoteLine : orderNoteLinesList) {
            PrintOrderNoteLine printOrderNoteLine = new PrintOrderNoteLine();
            printOrderNoteLine.setPrice(orderNoteLine.getPrice());
            printOrderNoteLine.setQuantity(orderNoteLine.getQuantity());
            double totalLinePrice = orderNoteLine.getPrice();
            printOrderNoteLine.setTotalLinePrice(totalLinePrice);
            if (orderNoteLine.getProduct() != null) {
                Product product = productService.getProduct(orderNoteLine.getProduct().getId());
                printOrderNoteLine.setMark(product.getMark());
                printOrderNoteLine.setModel(product.getModel());
                printOrderNoteLine.setColor(product.getColor());
            } else {
                pack = packService.getPack(orderNoteLine.getPack().getId());
                printOrderNoteLine.setPackName(pack.getName());
            }
            printOrderNoteLine.setPrintOrderNote(addedPrintOrderNote);
            printOrderNoteLineRepository.save(printOrderNoteLine);

            if (orderNoteLine.getProduct() == null) {

                List<PackProducts> packProductsList = packProductsService.getPackProducts(pack);
                for (PackProducts packProducts : packProductsList) {
                    Product product = packProducts.getProduct();
                    PrintOrderNoteLine newPrintOrderNoteLine = new PrintOrderNoteLine();
                    newPrintOrderNoteLine.setPrice(0);
                    newPrintOrderNoteLine.setTotalLinePrice(0);
                    newPrintOrderNoteLine.setMark(product.getMark());
                    newPrintOrderNoteLine.setModel(product.getModel());
                    newPrintOrderNoteLine.setColor(product.getColor());
                    newPrintOrderNoteLine.setQuantity(packProducts.getQuantity() * orderNoteLine.getQuantity());
                    newPrintOrderNoteLine.setPrintOrderNote(addedPrintOrderNote);
                    printOrderNoteLineRepository.save(newPrintOrderNoteLine);
                }
            }

        }

    }

}
*/
