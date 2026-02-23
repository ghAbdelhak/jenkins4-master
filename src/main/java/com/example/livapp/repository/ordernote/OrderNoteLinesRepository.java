package com.example.livapp.repository.ordernote;

import com.example.livapp.model.ordernote.OrderNoteLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderNoteLinesRepository extends JpaRepository<OrderNoteLine, Long> {
}
