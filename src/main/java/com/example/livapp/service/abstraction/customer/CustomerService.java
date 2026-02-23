package com.example.livapp.service.abstraction.customer;


import com.example.livapp.model.customer.Customer;
import com.example.livapp.model.customer.CustomerDTO;
import com.example.livapp.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomersWithDebt();

    Customer getCustomerById(long id);
    void addOrUpdateCustomer(Customer customer);


     Page<CustomerDTO> findCustomers(int pageNo, int pageSize);


}
