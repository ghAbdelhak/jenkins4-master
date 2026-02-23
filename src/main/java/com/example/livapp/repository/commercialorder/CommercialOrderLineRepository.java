package com.example.livapp.repository.commercialorder;

import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialorder.CommercialOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercialOrderLineRepository extends JpaRepository<CommercialOrderLine, Long> {

    List<CommercialOrderLine> findCommercialOrderLinesByCommercialOrder(CommercialOrder commercialOrder);

}
