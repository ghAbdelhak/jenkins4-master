package com.example.livapp.service.abstraction.commercialstock;

import com.example.livapp.model.commercialstock.CommercialStock;
import com.example.livapp.model.types.OrderNoteType;
import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.product.Product;
import com.example.livapp.model.user.User;

public interface CommercialStockService {

    // update personal commercial stock in the case of exit order or delivery
    void updateCommercialStock(User user, Product product, Pack pack, int quantity, OrderNoteType orderNoteType);

    void addCommercialStockHistory(CommercialStock commercialStock);
}
