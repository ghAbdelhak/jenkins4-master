package com.example.livapp.service.implementation.pack;

import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackProducts;
import com.example.livapp.repository.pack.PackProductsRepository;
import com.example.livapp.service.abstraction.pack.PackProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PackProductsServiceImpl implements PackProductsService {

    private final PackProductsRepository packProductsRepository;

    @Autowired
    public PackProductsServiceImpl(PackProductsRepository packProductsRepository) {
        this.packProductsRepository = packProductsRepository;
    }

    @Override
    public void addPackProducts(PackProducts packProducts) {
        packProductsRepository.save(packProducts);
    }

    @Override
    public List<PackProducts> getPackProducts(Pack pack) {
        return packProductsRepository.findPackProductsByPack(pack);
    }

    @Override
    public void deletePackProducts(Pack pack) {
        packProductsRepository.deleteAllByPack(pack);
    }
}
