package com.example.livapp.repository.commercialorder;

import com.example.livapp.model.commercialorder.CommercialOrder;
import com.example.livapp.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercialOrderRepository extends JpaRepository<CommercialOrder, Long> {

    //@Query(value = "SELECT ord From commercial_orders ord WHERE ord.archived = FALSE ORDER BY ord.id DESC")
    Page<CommercialOrder> findAllByArchivedIsFalseOrderByIdDesc(Pageable pageable);

    //@Query(value = "SELECT count(*)  From commercial_orders ord WHERE ord.state= 'Pending' or ord.state = 'Preparing'")
    int countAllByStateIn(List<String> states);

    List<CommercialOrder> findAllByStateIsAndArchived(String state, boolean archived);

}
