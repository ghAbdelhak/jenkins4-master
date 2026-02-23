package com.example.livapp.service.abstraction.product;

import com.example.livapp.model.product.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    void addProduct(Product product);

    void updateProduct(Product product);

    List<Product> getProducts(boolean state);

    Page<Product> findPaginated(int pageNo, int pageSize);

    Product getProduct(long id);
}
