package com.example.livapp.repository.ordernote;

import com.example.livapp.model.ordernote.PurchaseOrderNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderNoteRepository extends JpaRepository<PurchaseOrderNote, Long> {

}
