package com.example.restaurantbusiness.service;

import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.entity.Product;
import com.example.restaurantbusiness.repository.CustomerRepository;
import com.example.restaurantbusiness.repository.ProductWishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ProductWishRepository productWishRepository;

    public CustomerService(CustomerRepository customerRepository,
                           ProductWishRepository productWishRepository) {
        this.customerRepository = customerRepository;
        this.productWishRepository = productWishRepository;
    }

    public List<Customer> getAllRegisteredCustomers() {
        return customerRepository.findAllCustomerBasedOnRegistration(Boolean.TRUE);
    }

    public List<Product> getWishListByCustomerId(Integer customerId) {
        return productWishRepository.findAllProductsByCustomerId(customerId);
    }
}
