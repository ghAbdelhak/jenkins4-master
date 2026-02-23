package com.example.livapp.repository.commercialordernote;

import com.example.livapp.model.commercialordernote.ExitCommercialOrderNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExitCommercialOrderNoteRepository extends JpaRepository<ExitCommercialOrderNote, Long> {
    ExitCommercialOrderNote findByCommercialOrderId(long orderId);

}
