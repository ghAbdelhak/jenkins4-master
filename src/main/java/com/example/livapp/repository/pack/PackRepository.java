package com.example.livapp.repository.pack;


import com.example.livapp.model.pack.Pack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    //@Query(value = "SELECT pk From packs  pk ORDER BY pk.id DESC")
    Page<Pack> findAllByOrderByIdDesc(Pageable pageable);

}
