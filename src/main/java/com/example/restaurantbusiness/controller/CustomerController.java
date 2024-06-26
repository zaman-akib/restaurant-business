package com.example.restaurantbusiness.controller;

import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.entity.Product;
import com.example.restaurantbusiness.service.CustomerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{customerId}/wish-list")
    public ResponseEntity<List<Product>> getWishList(@PathVariable Integer customerId) {
        log.info("Getting wish list for customer ID: " + customerId);
        List<Product> wishList = customerService.getWishListByCustomerId(customerId);
        log.info("Successfully retrieved wish list for customer ID: " + customerId);
        return ResponseEntity.ok(wishList);
    }
}
