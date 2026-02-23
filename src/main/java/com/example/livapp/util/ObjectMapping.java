package com.example.livapp.util;

import com.example.livapp.model.commercialstock.CommercialStock;
import com.example.livapp.model.commercialstock.CommercialStockHistory;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackHistory;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.product.ProductHistory;

public class ObjectMapping {

    public static ProductHistory mapProductToProductHistory(Product product) {
        ProductHistory productHistory = new ProductHistory();
        productHistory.setProduct(product);
        productHistory.setPrice(product.getPrice());
        productHistory.setQuantity(product.getQuantity());
        productHistory.setReservedOrders(product.getReservedOrders());
        productHistory.setReservedPacks(product.getReservedPacks());
        productHistory.setReservedQuantity(product.getReservedQuantity());
        productHistory.setState(product.getState());
        return productHistory;
    }


    public static PackHistory mapPackToPackHistory(Pack pack) {
        PackHistory packHistory = new PackHistory();
        packHistory.setPack(pack);
        packHistory.setPrice(pack.getPrice());
        packHistory.setQuantity(pack.getQuantity());
        packHistory.setState(pack.isState());
        return packHistory;
    }


    public static CommercialStockHistory mapCommercialStockToCommercialStockHistory(CommercialStock commercialStock) {
        CommercialStockHistory commercialStockHistory = new CommercialStockHistory();
        commercialStockHistory.setUser(commercialStock.getUser());
        commercialStockHistory.setPack(commercialStock.getPack());
        commercialStockHistory.setProduct(commercialStock.getProduct());
        commercialStockHistory.setQuantity(commercialStock.getQuantity());
        return commercialStockHistory;
    }

}
