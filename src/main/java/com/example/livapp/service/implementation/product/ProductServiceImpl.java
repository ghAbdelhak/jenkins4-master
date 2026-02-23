package com.example.livapp.service.implementation.product;

import com.example.livapp.model.product.Product;
import com.example.livapp.model.product.ProductHistory;
import com.example.livapp.model.user.User;
import com.example.livapp.repository.product.ProductRepository;
import com.example.livapp.service.abstraction.product.ProductHistoryService;
import com.example.livapp.service.abstraction.product.ProductService;
import com.example.livapp.service.abstraction.user.UserService;
import com.example.livapp.util.ObjectMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductHistoryService productHistoryService;

    private final UserService userService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductHistoryService productHistoryService, UserService userService) {
        this.productRepository = productRepository;
        this.productHistoryService = productHistoryService;
        this.userService = userService;
    }


    @Override
    @Transactional
    public void addProduct(Product product) {
        // Create Product History
        User user = userService.getConnectedUser();
        ProductHistory productHistory = ObjectMapping.mapProductToProductHistory(product);
        productHistory.setAction("Update");
        productHistory.setChangedBy(user.getFirstName()+" "+user.getLastName());
        // Save Product and add it to Product History
        productRepository.save(product);
        productHistoryService.addProductHistory(productHistory);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);

    }


    @Override
    public List<Product> getProducts(boolean state) {
        return productRepository.findProductsByState(state);
    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepository.findAllByOrderByMarkAsc(pageable);

    }

    @Override
    public Product getProduct(long id) {
        return productRepository.findById(id).orElse(null);
    }
}
