package com.example.livapp.repository.stock;

import com.example.livapp.model.stock.StockEntry;
import com.example.livapp.model.stock.StockEntryLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockEntryLinesRepository extends JpaRepository<StockEntryLine, Long> {
    List<StockEntryLine> findStockEntryLinesByStockEntry(StockEntry stockEntry);

    void deleteAllByStockEntry(StockEntry stockEntry);

}
