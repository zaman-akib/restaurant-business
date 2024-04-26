package com.example.restaurantbusiness.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "customer_order")
@Getter
@Setter
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NonNull
    private Integer quantity;

    @NonNull
    private BigDecimal price;

    @NonNull
    private LocalDateTime orderDateTime;
}
