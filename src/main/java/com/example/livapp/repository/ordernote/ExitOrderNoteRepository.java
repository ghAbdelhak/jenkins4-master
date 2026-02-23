package com.example.livapp.repository.ordernote;

import com.example.livapp.model.ordernote.ExitOrderNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExitOrderNoteRepository extends JpaRepository<ExitOrderNote, Long> {
    ExitOrderNote findByOrderId(long orderId);

}
