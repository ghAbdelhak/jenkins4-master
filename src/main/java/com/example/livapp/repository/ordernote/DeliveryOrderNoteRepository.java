package com.example.livapp.repository.ordernote;

import com.example.livapp.model.ordernote.DeliveryOrderNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryOrderNoteRepository extends JpaRepository<DeliveryOrderNote, Long> {


}
