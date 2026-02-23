package com.example.livapp.model.stock;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class StockEntryHolder {

    @Valid
    private StockEntry stockEntry;

    private List<StockEntryLine> stockEntryLines;



}
