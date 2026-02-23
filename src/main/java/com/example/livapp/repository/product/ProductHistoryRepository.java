package com.example.livapp.repository.product;

import com.example.livapp.model.product.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
}
