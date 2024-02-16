package com.example.restaurantbusiness.controller;

import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.service.CustomerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/registered/all")
    public ResponseEntity<List<Customer>> getAllRegisteredCustomers() {
        log.info("Getting all registered customers in the system");
        List<Customer> customers = customerService.getAllRegisteredCustomers();
        log.info("Successfully retrieved all registered customers in the system");
        return ResponseEntity.ok(customers);
    }
}
