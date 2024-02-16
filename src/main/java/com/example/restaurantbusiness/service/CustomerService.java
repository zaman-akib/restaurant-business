package com.example.restaurantbusiness.service;

import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllRegisteredCustomers() {
        return customerRepository.findAllCustomerBasedOnRegistration(Boolean.TRUE);
    }
}
