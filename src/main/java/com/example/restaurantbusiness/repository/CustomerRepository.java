package com.example.restaurantbusiness.repository;

import com.example.restaurantbusiness.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select c from Customer c where c.isRegistered = :isRegistered")
    List<Customer> findAllCustomerBasedOnRegistration(Boolean isRegistered);
}
