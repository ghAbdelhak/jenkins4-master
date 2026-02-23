package com.example.livapp.service.implementation.product;

import com.example.livapp.model.product.ProductHistory;
import com.example.livapp.repository.product.ProductHistoryRepository;
import com.example.livapp.service.abstraction.product.ProductHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductHistoryServiceImpl implements ProductHistoryService {

    private final ProductHistoryRepository productHistoryRepository;

    @Autowired
    public ProductHistoryServiceImpl(ProductHistoryRepository productHistoryRepository) {
        this.productHistoryRepository = productHistoryRepository;
    }

    @Override
    public void addProductHistory(ProductHistory productHistory) {
        productHistoryRepository.save(productHistory);
    }


}
