package com.example.restaurantbusiness.repository;

import com.example.restaurantbusiness.entity.CustomerOrder;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByOrderDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
