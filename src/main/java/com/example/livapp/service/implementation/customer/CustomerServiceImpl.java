package com.example.livapp.service.implementation.customer;

import com.example.livapp.model.customer.Customer;
import com.example.livapp.model.customer.CustomerDTO;
import com.example.livapp.model.order.Order;
import com.example.livapp.repository.customer.CustomerRepository;
import com.example.livapp.service.abstraction.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomersWithDebt() {
        return customerRepository.findAllByTotalDebtIsGreaterThan(0.0);
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void addOrUpdateCustomer(Customer customer) {
        customerRepository.save(customer);
    }


    @Override
    public Page<CustomerDTO> findCustomers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return CustomerDTO.toDtoPage(customerRepository.findAll(pageable));

        }
    }

