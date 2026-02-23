package com.example.livapp.repository.stock;

import com.example.livapp.model.stock.StockEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {

    //@Query(value = "SELECT se From stock_entry se ORDER BY se.entryDate DESC")
    Page<StockEntry> findAllByOrderByEntryDateDesc(Pageable pageable);

}
