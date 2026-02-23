package com.example.livapp;

import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackHolder;
import com.example.livapp.model.pack.PackProducts;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.stock.StockEntry;
import com.example.livapp.model.stock.StockEntryHolder;
import com.example.livapp.model.stock.StockEntryLine;
import com.example.livapp.model.supplier.Supplier;
import com.example.livapp.model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtil {

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@email.com");
        return user;
    }

    public static Product getProduct(String barcode,String mark,String model,String color, int qte, double price) {
        Product product = new Product();
        product.setBarCode(barcode);
        product.setMark(mark);
        product.setModel(model);
        product.setColor(color);
        product.setQuantity(qte);
        product.setPrice(price);
        product.setDescription("Description1");
        product.setType("Type1");
        return product;
    }

    public static PackHolder getPackHolder(List<Product> products,List<Integer> quantities ,String packName, int qte, double price) {
        int i = 0;
        PackHolder packHolder = new PackHolder();
        // create the pack
        Pack pack = new Pack();
        pack.setName(packName);
        pack.setQuantity(qte);
        pack.setPrice(price);
        // create pack products
        List<PackProducts> packProductsList = new ArrayList<>();
        for(Product product : products) {
            PackProducts packProducts = new PackProducts();
            packProducts.setProduct(product);
            packProducts.setQuantity(quantities.get(i));
            packProducts.setPack(pack);
            packProductsList.add(packProducts);
            i++;
        }
        packHolder.setPack(pack);
        packHolder.setPackProductsList(packProductsList);
        return packHolder;
    }

    public static StockEntryHolder getStockEntryHolder(String entryNumber, List<Product> products, Supplier supplier, Date date) {
        StockEntryHolder stockEntryHolder = new StockEntryHolder();
        StockEntry stockEntry = new StockEntry();
        List<StockEntryLine> stockEntryLines = new ArrayList<>();
        int totalQuantity = products.stream()
                .mapToInt(Product::getQuantity)
                .sum();

        double totalPrice = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
        // create Stock entry
        stockEntry.setEntryNumber(entryNumber);
        stockEntry.setTotalPrice(totalPrice*totalQuantity);
        stockEntry.setTotalQuantity(totalQuantity);
        stockEntry.setEntryDate(date);
        stockEntry.setSupplier(supplier);

        // create stock entry line
        for(Product product : products) {
            StockEntryLine stockEntryLine = new StockEntryLine();
            stockEntryLine.setQuantity(product.getQuantity());
            stockEntryLine.setPrice(product.getPrice());
            stockEntryLine.setProduct(product);
            stockEntryLine.setStockEntry(stockEntry);
            stockEntryLines.add(stockEntryLine);
        }
        stockEntryHolder.setStockEntryLines(stockEntryLines);
        stockEntryHolder.setStockEntry(stockEntry);
        return stockEntryHolder;

    }


    public static Supplier getSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier 1");
        supplier.setEmail("email@email.com");
        supplier.setAddress("address");
        supplier.setHomePhone("021212121");
        supplier.setMobilePhone("0554545455");
        return supplier;
    }



}
