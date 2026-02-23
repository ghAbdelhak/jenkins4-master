package com.example.livapp.service.abstraction.stock;

import com.example.livapp.model.stock.StockEntry;
import com.example.livapp.model.stock.StockEntryHolder;
import org.springframework.data.domain.Page;

public interface StockEntryService {

    StockEntry addStockEntry(StockEntryHolder stockEntryHolder);

    Page<StockEntry> findPaginated(int pageNo, int pageSize);

    StockEntryHolder getStockEntryDetails(long id);

    StockEntryHolder getEmptyStockEntryHolder();

    StockEntryHolder getStockEntryForUpdate(long id);

    void updateStockEntry(StockEntryHolder stockEntryHolder);

    StockEntry getStockEntry(long id);

}
