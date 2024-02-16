package com.example.restaurantbusiness.controller;

import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.service.CustomerService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/registered/all")
    public ResponseEntity<List<Customer>> getAllRegisteredCustomers() {
        return ResponseEntity.ok(customerService.getAllRegisteredCustomers());
    }
}
