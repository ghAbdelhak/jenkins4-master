package com.example.livapp;

import com.example.livapp.model.pack.PackHolder;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.stock.StockEntryHolder;
import com.example.livapp.model.supplier.Supplier;
import com.example.livapp.service.abstraction.user.UserService;
import com.example.livapp.service.implementation.pack.PackServiceImpl;
import com.example.livapp.service.implementation.product.ProductServiceImpl;
import com.example.livapp.service.implementation.stock.StockEntryServiceImpl;
import com.example.livapp.service.implementation.supplier.SupplierServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class StockEntryServiceIntegrationTest {


    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private StockEntryServiceImpl stockEntryService;

    @Autowired
    private SupplierServiceImpl supplierService;

    @MockBean
    private UserService userService;



    @Test
    void testAddAndStockEntry() {
        // create Supplier
        Supplier supplier = TestUtil.getSupplier();
        supplierService.addSupplier(supplier);
        // Add Products
        Mockito.when(userService.getConnectedUser()).thenReturn(TestUtil.getUser());
        Product product1 = TestUtil.getProduct("barcode1","Mark1","modal1","color1",100,20000);
        Product product2 = TestUtil.getProduct("barcode2","Mark2","modal2","color2",200,50000);
        productService.addProduct(product1);
        productService.addProduct(product2);
        // Get Products
        product1 = productService.getProduct(1L);
        product1.setQuantity(50);
        product2 = productService.getProduct(2L);
        product2.setQuantity(30);
        List<Product> products = List.of(product1, product2);
         // Create Stock entry holder
        StockEntryHolder stockEntryHolder = TestUtil.getStockEntryHolder("112",products,supplier,new Date());
        stockEntryService.addStockEntry(stockEntryHolder);
        stockEntryHolder = stockEntryService.getStockEntryDetails(1);
        Assertions.assertEquals(stockEntryHolder.getStockEntryLines().size(),2);
        // verify total quantities and price
        Assertions.assertEquals(stockEntryHolder.getStockEntry().getTotalQuantity(),80);
        Assertions.assertEquals(stockEntryHolder.getStockEntry().getTotalPrice(),20000*50+50000*30);
        // Verify products quantities
        product1 = productService.getProduct(1L);
        product2 = productService.getProduct(2L);
        Assertions.assertEquals(product1.getQuantity(),150);
        Assertions.assertEquals(product2.getQuantity(),230);
        
    }



}
