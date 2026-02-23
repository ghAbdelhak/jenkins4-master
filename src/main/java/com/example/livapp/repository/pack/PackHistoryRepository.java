package com.example.livapp.repository.pack;

import com.example.livapp.model.pack.PackHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackHistoryRepository extends JpaRepository<PackHistory, Long> {
}
