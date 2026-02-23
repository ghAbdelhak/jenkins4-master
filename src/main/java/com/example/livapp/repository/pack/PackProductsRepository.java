package com.example.livapp.repository.pack;

import com.example.livapp.model.pack.Pack;
import com.example.livapp.model.pack.PackProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackProductsRepository extends JpaRepository<PackProducts, Long> {

    List<PackProducts> findPackProductsByPack(Pack pack);

    void deleteAllByPack(Pack pack);
}
