/*
package com.example.livapp.service.implementation.print;

import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialordernote.CommercialOrderNoteLine;
import com.example.livapp.model.commercialordernote.ExitCommercialOrderNote;
import com.example.livapp.model.ordernote.ExitOrderNote;
import com.example.livapp.model.ordernote.OrderNote;
import com.example.livapp.model.ordernote.OrderNoteLine;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackProducts;
import com.example.livapp.model.print.PrintCommercialOrderNote;
import com.example.livapp.model.print.PrintCommercialOrderNoteLine;
import com.example.livapp.model.print.PrintOrderNote;
import com.example.livapp.model.print.PrintOrderNoteLine;
import com.example.livapp.model.product.Product;
import com.example.livapp.repository.print.PrintCommercialOrderNoteLineRepository;
import com.example.livapp.repository.print.PrintCommercialOrderNoteRepository;
import com.example.livapp.repository.print.PrintOrderNoteLineRepository;
import com.example.livapp.service.abstraction.commercialorder.CommercialOrderService;
import com.example.livapp.service.abstraction.order.OrderService;
import com.example.livapp.service.abstraction.pack.PackProductsService;
import com.example.livapp.service.abstraction.pack.PackService;
import com.example.livapp.service.abstraction.print.PrintCommercialOrderNoteService;
import com.example.livapp.service.abstraction.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrintCommercialOrderNoteServiceImpl implements PrintCommercialOrderNoteService {


    private final PrintCommercialOrderNoteLineRepository printCommercialOrderNoteLineRepository;
    private final PrintOrderNoteLineRepository printOrderNoteLineRepository;
    private final ProductService productService;
    private final PackService packService;
    private final PackProductsService packProductsService;
    private final PrintCommercialOrderNoteRepository printCommercialOrderNoteRepository;
    private final CommercialOrderService commercialOrderService;

    @Autowired
    public PrintCommercialOrderNoteServiceImpl(PrintCommercialOrderNoteLineRepository printCommercialOrderNoteLineRepository, ProductService productService, PackService packService, PackProductsService packProductsService, PrintOrderNoteLineRepository printOrderNoteLineRepository, PrintCommercialOrderNoteRepository printCommercialOrderNoteRepository,CommercialOrderService commercialOrderService) {
        this.printCommercialOrderNoteLineRepository = printCommercialOrderNoteLineRepository;
        this.productService = productService;
        this.packService = packService;
        this.packProductsService = packProductsService;
        this.printOrderNoteLineRepository = printOrderNoteLineRepository;
        this.printCommercialOrderNoteRepository = printCommercialOrderNoteRepository;
        this.commercialOrderService = commercialOrderService;
    }

    @Override
    @Transactional
    public void addPrintCommercialOrderNote(OrderNote orderNote,OrderNoteType orderNoteType ) {
        PrintCommercialOrderNote printCommercialOrderNote = new PrintCommercialOrderNote();
        ExitCommercialOrderNote exitCommercialOrderNote = null;
        List<CommercialOrderNoteLine> commercialOrderNoteLines = new ArrayList<>();
        long commercialOrderId = 0;
        Pack pack = null;
        if(orderNoteType.getType().equals(OrderNoteType.EXIT.getType())) {
            exitCommercialOrderNote = (ExitCommercialOrderNote) orderNote;
            commercialOrderId = exitCommercialOrderNote.getCommercialOrder().getId();
            commercialOrderNoteLines = exitCommercialOrderNote.getCommercialOrderNoteLines();
        }
        CommercialOrder commercialOrder = commercialOrderService.getCommercialOrder(commercialOrderId);
        // Calculate total prices (price * quantity for each line)
        double totalPrices = commercialOrderNoteLines.stream()
                .mapToDouble(line -> line.getPrice() * line.getQuantity())
                .sum();
        // setters for printOrderNote
        printCommercialOrderNote.setCommercialOrder(commercialOrder);
        printCommercialOrderNote.setType(orderNoteType.getType());
        printCommercialOrderNote.setTotalPrice(totalPrices);
        // add PrintOrderNote
        PrintCommercialOrderNote addedPrintCommercialOrderNote = printCommercialOrderNoteRepository.save(printCommercialOrderNote);

        // Get Lines
        for (CommercialOrderNoteLine commercialOrderNoteLine : commercialOrderNoteLines) {
            PrintCommercialOrderNoteLine printCommercialOrderNoteLine = new PrintCommercialOrderNoteLine();
            printCommercialOrderNoteLine.setPrice(commercialOrderNoteLine.getPrice());
            printCommercialOrderNoteLine.setQuantity(commercialOrderNoteLine.getQuantity());
            double totalLinePrice = commercialOrderNoteLine.getPrice();
            printCommercialOrderNoteLine.setTotalLinePrice(totalLinePrice);
            if (commercialOrderNoteLine.getProduct() != null) {
                Product product = productService.getProduct(commercialOrderNoteLine.getProduct().getId());
                printCommercialOrderNoteLine.setMark(product.getMark());
                printCommercialOrderNoteLine.setModel(product.getModel());
                printCommercialOrderNoteLine.setColor(product.getColor());
            } else {
                pack = packService.getPack(commercialOrderNoteLine.getPack().getId());
                printCommercialOrderNoteLine.setPackName(pack.getName());
            }
            printCommercialOrderNoteLine.setPrintCommercialOrderNote(addedPrintCommercialOrderNote);
            printCommercialOrderNoteLineRepository.save(printCommercialOrderNoteLine);

            if (commercialOrderNoteLine.getProduct() == null) {

                List<PackProducts> packProductsList = packProductsService.getPackProducts(pack);
                for (PackProducts packProducts : packProductsList) {
                    Product product = packProducts.getProduct();
                    PrintCommercialOrderNoteLine newPrintCommercialOrderNoteLine = new PrintCommercialOrderNoteLine();
                    newPrintCommercialOrderNoteLine.setPrice(0);
                    newPrintCommercialOrderNoteLine.setTotalLinePrice(0);
                    newPrintCommercialOrderNoteLine.setMark(product.getMark());
                    newPrintCommercialOrderNoteLine.setModel(product.getModel());
                    newPrintCommercialOrderNoteLine.setColor(product.getColor());
                    newPrintCommercialOrderNoteLine.setQuantity(packProducts.getQuantity() * commercialOrderNoteLine.getQuantity());
                    newPrintCommercialOrderNoteLine.setPrintCommercialOrderNote(addedPrintCommercialOrderNote);
                    printCommercialOrderNoteLineRepository.save(newPrintCommercialOrderNoteLine);
                }
            }

        }


    }
}
*/
