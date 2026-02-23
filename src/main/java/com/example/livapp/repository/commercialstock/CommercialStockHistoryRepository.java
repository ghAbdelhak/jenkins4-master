package com.example.livapp.repository.commercialstock;

import com.example.livapp.model.commercialstock.CommercialStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialStockHistoryRepository extends JpaRepository<CommercialStockHistory, Long> {

}
