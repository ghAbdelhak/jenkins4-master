package com.example.livapp.util;

import com.example.livapp.model.commercialorder.CommercialOrderLine;
import com.example.livapp.model.commercialordernote.CommercialOrderNoteLine;
import com.example.livapp.model.commercialordernote.ExitCommercialOrderNote;
import com.example.livapp.model.order.OrderLine;
import com.example.livapp.model.ordernote.ExitOrderNote;
import com.example.livapp.model.ordernote.OrderNoteLine;
import com.example.livapp.model.pack.PackProducts;
import com.example.livapp.model.stock.StockEntryLine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class ListUtil {

    public static <T> boolean isExist(T item, List<T> itemList, BiPredicate<T, T> comparator) {
        for (T listItem : itemList) {
            if (comparator.test(item, listItem)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isExistProductPacks(PackProducts packProduct, List<PackProducts> packProductsList) {
        return ListUtil.isExist(packProduct, packProductsList,
                (p1, p2) -> p1.getProduct().getId() == p2.getProduct().getId());
    }

    public static boolean isExistProductsStockEntry(StockEntryLine stockEntryLine, List<StockEntryLine> stockEntryLinesList) {
        return ListUtil.isExist(stockEntryLine, stockEntryLinesList,
                (s1, s2) -> s1.getProduct().getId() == s2.getProduct().getId());
    }

    public static List<OrderNoteLine> createOrderNoteLines(List<OrderLine> orderLineList, ExitOrderNote exitOrderNote) {
        List<OrderNoteLine> orderNoteLinesList = new ArrayList<>();
        for (OrderLine orderLine : orderLineList) {
            OrderNoteLine orderNoteLines = new OrderNoteLine();
            orderNoteLines.setExitOrderNote(exitOrderNote);
            orderNoteLines.setProduct(orderLine.getProduct());
            orderNoteLines.setPack(orderLine.getPack());
            orderNoteLines.setQuantity(orderLine.getQuantity());
            orderNoteLines.setPrice(orderLine.getPrice());
            orderNoteLinesList.add(orderNoteLines);
        }
        return orderNoteLinesList;
    }

    public static List<CommercialOrderNoteLine> createCommercialOrderNoteLines(List<CommercialOrderLine> commercialOrderLines, ExitCommercialOrderNote exitCommercialOrderNote) {
        List<CommercialOrderNoteLine> commercialOrderNoteLines = new ArrayList<>();
        for (CommercialOrderLine commercialOrderLine : commercialOrderLines) {
            CommercialOrderNoteLine commercialOrderNoteLine = new CommercialOrderNoteLine();
            commercialOrderNoteLine.setExitCommercialOrderNote(exitCommercialOrderNote);
            commercialOrderNoteLine.setProduct(commercialOrderLine.getProduct());
            commercialOrderNoteLine.setPack(commercialOrderLine.getPack());
            commercialOrderNoteLine.setQuantity(commercialOrderLine.getQuantity());
            commercialOrderNoteLine.setPrice(commercialOrderLine.getPrice());
            commercialOrderNoteLines.add(commercialOrderNoteLine);
        }
        return commercialOrderNoteLines;


    }
}



    /*

     public static boolean isExistProductsStockEntry(PackProducts packProduct, List<PackProducts> packProductsList) {
    boolean exist = false;
    for (PackProducts p : packProductsList) {
        if (packProduct.getProduct().getId()==(p.getProduct().getId())) {
            exist = true;
            break;
        }
    }
    return exist;
    }



    public static boolean isExistProductsStockEntry(StockEntryLines stockEntryLine, List<StockEntryLines> stockEntryLinesList) {
        boolean exist = false;
        for (StockEntryLines entryLine : stockEntryLinesList) {
            if (stockEntryLine.getProduct().getId()==(entryLine.getProduct().getId())) {
                exist = true;
                break;
            }
        }
        return exist;
    }

}

 */

