package com.example.livapp.service.abstraction.stock;

import com.example.livapp.model.stock.StockEntry;
import com.example.livapp.model.stock.StockEntryLine;

import java.util.List;

public interface StockEntryLinesService {

    void addStockEntryLine(StockEntryLine stockEntryLine);

    List<StockEntryLine> getStockEntriesLines(StockEntry stockEntry);

    void deleteStockEntryLine(StockEntry stockEntry);
}

