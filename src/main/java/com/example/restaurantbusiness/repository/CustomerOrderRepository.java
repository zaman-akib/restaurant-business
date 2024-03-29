package com.example.restaurantbusiness.repository;

import com.example.restaurantbusiness.entity.CustomerOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByOrderDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<CustomerOrder> findByCustomerId(Long customerId);

    @Query("select date(co.orderDateTime) as saleDay, sum(co.price) as totalSaleAmount " +
        "from CustomerOrder co " +
        "where co.orderDateTime between :startDate and :endDate " +
        "group by date(co.orderDateTime) " +
        "order by totalSaleAmount desc " +
        "limit 1")
    List<Object[]> findMaxSaleDay(LocalDateTime startDate, LocalDateTime endDate);
}
