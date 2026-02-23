package com.example.livapp.repository.commercialordernote;

import com.example.livapp.model.commercialordernote.CommercialOrderNoteLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialOrderNoteLineRepository extends JpaRepository<CommercialOrderNoteLine,Long> {
}
