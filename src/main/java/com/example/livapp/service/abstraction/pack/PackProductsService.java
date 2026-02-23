package com.example.livapp.service.abstraction.pack;

import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackProducts;

import java.util.List;

public interface PackProductsService {

    void addPackProducts(PackProducts packProducts);

    List<PackProducts> getPackProducts(Pack pack);

    void deletePackProducts(Pack pack);

}
