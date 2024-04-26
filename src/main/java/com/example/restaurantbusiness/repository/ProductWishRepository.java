package com.example.restaurantbusiness.repository;

import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductWishRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select pw.product from ProductWish pw where pw.customer.id = :customerId")
    List<Product> findAllProductsByCustomerId(Integer customerId);
}
