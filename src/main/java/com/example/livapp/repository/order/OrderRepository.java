package com.example.livapp.repository.order;

import com.example.livapp.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    //@Query(value = "SELECT ord From orders ord WHERE ord.archived = FALSE ORDER BY ord.id DESC")
    Page<Order> findAllByArchivedIsFalseOrderByIdDesc(Pageable pageable);

    //@Query(value = "SELECT count(*)  From orders ord WHERE ord.state= 'Pending' or ord.state = 'Preparing'")
    int countAllByStateIn(List<String> states);

    List<Order> findAllByStateIsAndArchived(String state, boolean archived);
}
