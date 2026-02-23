package com.example.livapp.service.implementation.stock;

import com.example.livapp.model.product.Product;
import com.example.livapp.model.stock.StockEntry;
import com.example.livapp.model.stock.StockEntryHolder;
import com.example.livapp.model.stock.StockEntryLine;
import com.example.livapp.repository.stock.StockEntryRepository;
import com.example.livapp.service.abstraction.product.ProductService;
import com.example.livapp.service.abstraction.stock.StockEntryLinesService;
import com.example.livapp.service.abstraction.stock.StockEntryService;
import com.example.livapp.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockEntryServiceImpl implements StockEntryService {

    private final StockEntryRepository stockEntryRepository;

    private final ProductService productService;

    private final StockEntryLinesService stockEntryLinesService;

    @Autowired
    public StockEntryServiceImpl(StockEntryRepository stockEntryRepository, ProductService productService, StockEntryLinesService stockEntryLinesService) {
        this.stockEntryRepository = stockEntryRepository;
        this.productService = productService;
        this.stockEntryLinesService = stockEntryLinesService;
    }

    @Override
    @Transactional
    public StockEntry addStockEntry(StockEntryHolder stockEntryHolder) {
        int totalQuantity = 0;
        double totalPrice = 0;
        StockEntry stockEntry = stockEntryHolder.getStockEntry();
        List<StockEntryLine> stockEntryLinesList = stockEntryHolder.getStockEntryLines();
        StockEntry addedStockEntry = stockEntryRepository.saveAndFlush(stockEntry);
        for (StockEntryLine stockEntryLine : stockEntryLinesList) {
            int quantityProduct = stockEntryLine.getQuantity();
            double newPrice = stockEntryLine.getPrice();
            if (quantityProduct > 0) {
                totalQuantity = totalQuantity + quantityProduct;
                totalPrice = totalPrice + newPrice * quantityProduct;
                long idProduct = stockEntryLine.getProduct().getId();
                Product product = productService.getProduct(idProduct);
                int newQuantity = product.getQuantity() + quantityProduct;
                product.setQuantity(newQuantity);
                product.setPrice(newPrice);
                stockEntryLine.setStockEntry(addedStockEntry);
                stockEntryLinesService.addStockEntryLine(stockEntryLine);
                productService.addProduct(product);
            }

        }
        addedStockEntry.setTotalQuantity(totalQuantity);
        addedStockEntry.setTotalPrice(totalPrice);
        stockEntryRepository.save(addedStockEntry);
        return addedStockEntry;
    }

    @Override
    public Page<StockEntry> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return stockEntryRepository.findAllByOrderByEntryDateDesc(pageable);

    }

    @Override
    public StockEntryHolder getStockEntryDetails(long id) {
        Optional<StockEntry> optional = stockEntryRepository.findById(id);
        StockEntry stockEntry = new StockEntry();
        if (optional.isPresent()) {
            stockEntry = optional.get();
        }
        List<StockEntryLine> stockEntryLinesList = stockEntryLinesService.getStockEntriesLines(stockEntry);
        StockEntryHolder stockEntryHolder = new StockEntryHolder();
        stockEntryHolder.setStockEntry(stockEntry);
        stockEntryHolder.setStockEntryLines(stockEntryLinesList);
        return stockEntryHolder;
    }

    @Override
    public StockEntryHolder getEmptyStockEntryHolder() {
        StockEntry stockEntry = new StockEntry();
        StockEntryHolder stockEntryHolder = new StockEntryHolder();
        List<StockEntryLine> stockEntryLinesList = new ArrayList<>();
        List<Product> products = productService.getProducts(true);
        for (Product product : products) {
            StockEntryLine stockEntryLine = new StockEntryLine();
            stockEntryLine.setProduct(product);
            stockEntryLine.setStockEntry(stockEntry);
            stockEntryLine.setQuantity(0);
            stockEntryLine.setPrice(product.getPrice());
            stockEntryLinesList.add(stockEntryLine);
        }
        stockEntryHolder.setStockEntry(stockEntry);
        stockEntryHolder.setStockEntryLines(stockEntryLinesList);
        return stockEntryHolder;
    }

    @Override
    public StockEntryHolder getStockEntryForUpdate(long id) {
        StockEntryHolder stockEntryHolder = getStockEntryDetails(id);
        List<StockEntryLine> stockEntryLinesList = getEmptyStockEntryHolder().getStockEntryLines();
        for (StockEntryLine stockEntryLine : stockEntryLinesList) {
            if (!ListUtil.isExistProductsStockEntry(stockEntryLine, stockEntryHolder.getStockEntryLines())) {
                stockEntryHolder.getStockEntryLines().add(stockEntryLine);
            }
        }
        return stockEntryHolder;
    }

    @Override
    @Transactional
    public void updateStockEntry(StockEntryHolder stockEntryHolder) {
        StockEntry stockEntry = getStockEntry(stockEntryHolder.getStockEntry().getId());
        List<StockEntryLine> stockEntryLinesList = stockEntryLinesService.getStockEntriesLines(stockEntry);
        for (StockEntryLine stockEntryLine : stockEntryLinesList) {
            int quantityProduct = stockEntryLine.getQuantity();
            if (quantityProduct > 0) {
                long idProduct = stockEntryLine.getProduct().getId();
                Product product = productService.getProduct(idProduct);
                int newQuantity = product.getQuantity() - quantityProduct;
                // Update Product Quantity
                product.setQuantity(newQuantity);
                productService.addProduct(product);
                // Delete the entry line
                stockEntryLinesService.deleteStockEntryLine(stockEntry);
            }
        }
        addStockEntry(stockEntryHolder);
    }

    @Override
    public StockEntry getStockEntry(long id) {
        Optional<StockEntry> optional = stockEntryRepository.findById(id);
        StockEntry stockEntry = null;
        if (optional.isPresent()) {
            stockEntry = optional.get();
        }
        return stockEntry;
    }
}
