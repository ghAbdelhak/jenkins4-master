package com.example.livapp;


import com.example.livapp.model.product.Product;
import com.example.livapp.service.abstraction.user.UserService;
import com.example.livapp.service.implementation.product.ProductServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ProductServiceIntegrationTest {


    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private UserService userService;


    @Test
    void testAddAndGetProduct() {
        // Add Products
        Mockito.when(userService.getConnectedUser()).thenReturn(TestUtil.getUser());
        Product product1 = TestUtil.getProduct("barcode1","Mark1","modal1","color1",100,20000);
        Product product2 = TestUtil.getProduct("barcode2","Mark2","modal2","color2",200,50000);
        productService.addProduct(product1);
        productService.addProduct(product2);
        // Get Products
        Product product3 = productService.getProduct(1);
        Product product4 = productService.getProduct(2);
        Assertions.assertEquals("Mark1", product3.getMark());
        Assertions.assertEquals("Mark2", product4.getMark());
    }


}
