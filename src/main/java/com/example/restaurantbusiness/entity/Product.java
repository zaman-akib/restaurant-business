package com.example.restaurantbusiness.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String description;

    @NonNull
    private BigDecimal price;

    @NonNull
    private Integer stockAvailable;
}
