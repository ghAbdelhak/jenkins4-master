package com.example.livapp.repository.commercialorder;

import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.commercialorder.CommercialOrderStateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercialOrderStateHistoryRepository extends JpaRepository<CommercialOrderStateHistory, Long> {

    List<CommercialOrderStateHistory> findAllByCommercialOrder(CommercialOrder commercialOrder);
}
