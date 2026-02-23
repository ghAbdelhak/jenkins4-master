package com.example.livapp.service.implementation.stock;

import com.example.livapp.model.stock.StockEntry;
import com.example.livapp.model.stock.StockEntryLine;
import com.example.livapp.repository.stock.StockEntryLinesRepository;
import com.example.livapp.service.abstraction.stock.StockEntryLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockEntryLinesServiceImpl implements StockEntryLinesService {

    private final StockEntryLinesRepository stockEntryLinesRepository;

    @Autowired
    public StockEntryLinesServiceImpl(StockEntryLinesRepository stockEntryLinesRepository) {
        this.stockEntryLinesRepository = stockEntryLinesRepository;
    }

    @Override
    public void addStockEntryLine(StockEntryLine stockEntryLine) {
        stockEntryLinesRepository.save(stockEntryLine);
    }

    @Override
    public List<StockEntryLine> getStockEntriesLines(StockEntry stockEntry) {
        return stockEntryLinesRepository.findStockEntryLinesByStockEntry(stockEntry);
    }


    @Override
    public void deleteStockEntryLine(StockEntry stockEntry) {
        stockEntryLinesRepository.deleteAllByStockEntry(stockEntry);
    }
}
