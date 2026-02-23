package com.example.livapp.repository.commercialstock;

import com.example.livapp.model.commercialstock.CommercialStock;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialStockRepository extends JpaRepository<CommercialStock, Long> {

    CommercialStock findFirstByUserAndProduct(User user, Product product);

    CommercialStock findFirstByUserAndPack(User user, Pack pack);


}
