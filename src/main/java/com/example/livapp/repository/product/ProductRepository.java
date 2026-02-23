package com.example.livapp.repository.product;

import com.example.livapp.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByState(boolean state);

    //@Query(value = "SELECT pr From products  pr ORDER BY pr.mark")
    Page<Product> findAllByOrderByMarkAsc(Pageable pageable);


}
