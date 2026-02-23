package com.example.livapp.service.implementation.commercialstock;

import com.example.livapp.model.commercialstock.CommercialStock;
import com.example.livapp.model.commercialstock.CommercialStockHistory;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.user.User;
import com.example.livapp.repository.commercialstock.CommercialStockHistoryRepository;
import com.example.livapp.repository.commercialstock.CommercialStockRepository;
import com.example.livapp.service.abstraction.commercialstock.CommercialStockService;
import com.example.livapp.util.ObjectMapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommercialStockServiceImpl implements CommercialStockService {

    private final CommercialStockRepository commercialStockRepository;
    private final CommercialStockHistoryRepository commercialStockHistoryRepository;

    @Autowired
    public CommercialStockServiceImpl(CommercialStockRepository commercialStockRepository, CommercialStockHistoryRepository commercialStockHistoryRepository) {
        this.commercialStockRepository = commercialStockRepository;
        this.commercialStockHistoryRepository = commercialStockHistoryRepository;
    }


    @Override
    @Transactional
    public void updateCommercialStock(User user, Product product, Pack pack, int quantity, OrderNoteType orderNoteType) {
        CommercialStock commercialStock = new CommercialStock();
        int newQuantity =0;
        // Case Pack
        if(product!=null) {
            // find if the product exist in the commercial stock
            CommercialStock currentCommercialStock  = commercialStockRepository.findFirstByUserAndProduct(user,product);
            if(currentCommercialStock!=null) {
                // The product Exit
                commercialStock = currentCommercialStock;
                // If exit
                if(orderNoteType.getType().equals(OrderNoteType.EXIT.getType())) {
                newQuantity = commercialStock.getQuantity()+quantity;
                }
                // If Selled
                else {
                newQuantity = commercialStock.getQuantity()-quantity;
                }
                commercialStock.setQuantity(newQuantity);

            }
            // The product Does not Exit
            else {
                commercialStock.setQuantity(quantity);
                commercialStock.setUser(user);
                commercialStock.setProduct(product);
                commercialStock.setPack(null);
            }
        }
        // Case Pack
        else {
            // find if the pack exist in the commercial stock
            CommercialStock currentCommercialStock  = commercialStockRepository.findFirstByUserAndPack(user,pack);
            if(currentCommercialStock!=null) {
                // The pack Exit
                commercialStock = currentCommercialStock;
                // If exit
                if(orderNoteType.getType().equals(OrderNoteType.EXIT.getType())) {
                    newQuantity = commercialStock.getQuantity()+quantity;
                }
                // If Selled
                else {
                    newQuantity = commercialStock.getQuantity()-quantity;
                }
                commercialStock.setQuantity(newQuantity);

            }
            // The pack Does not Exit
            else {
                commercialStock.setQuantity(quantity);
                commercialStock.setUser(user);
                commercialStock.setProduct(null);
                commercialStock.setPack(pack);
            }
        }

        commercialStockRepository.save(commercialStock);
        addCommercialStockHistory(commercialStock);
    }

    @Override
    public void addCommercialStockHistory(CommercialStock commercialStock) {
        CommercialStockHistory commercialStockHistory = ObjectMapping.mapCommercialStockToCommercialStockHistory(commercialStock);
        commercialStockHistoryRepository.save(commercialStockHistory);
    }
}
