package com.example.livapp;


import com.example.livapp.model.pack.PackHolder;
import com.example.livapp.model.product.Product;
import com.example.livapp.service.abstraction.user.UserService;
import com.example.livapp.service.implementation.pack.PackServiceImpl;
import com.example.livapp.service.implementation.product.ProductServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PackServiceIntegrationTest {


    @Autowired
    private PackServiceImpl packService;

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private UserService userService;


    @Test
    void testAddAndGetPack() {
        // Add Products
        Mockito.when(userService.getConnectedUser()).thenReturn(TestUtil.getUser());
        Product product1 = TestUtil.getProduct("barcode1","Mark1","modal1","color1",100,20000);
        Product product2 = TestUtil.getProduct("barcode2","Mark2","modal2","color2",200,50000);
        productService.addProduct(product1);
        productService.addProduct(product2);
        // Get Products
        List<Product> products = productService.getProducts(true);
        // Create Packs
        PackHolder packHolder = TestUtil.getPackHolder(products, List.of(2,1),"Pack 1",5,80000);
        // Insert Pack
        packService.addPackHolder(packHolder);
        // Verify inserted pack
        PackHolder packHolder1 = packService.getPackDetails(1);
        Assertions.assertEquals(packHolder.getPackProductsList().size(), 2);
        // Get Product reserved Quantities
        product1 = productService.getProduct(1);
        product2 = productService.getProduct(2);
        Assertions.assertEquals(product1.getReservedPacks(),10);
        Assertions.assertEquals(product2.getReservedPacks(),5);

    }




}
