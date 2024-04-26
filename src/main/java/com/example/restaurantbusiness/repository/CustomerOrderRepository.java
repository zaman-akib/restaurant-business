package com.example.restaurantbusiness.repository;

import com.example.restaurantbusiness.entity.CustomerOrder;

import com.example.restaurantbusiness.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select co.product, sum(co.product.price * co.quantity) as total_sale_amount " +
        "from CustomerOrder co " +
        "group by co.product " +
        "order by total_sale_amount desc")
    Page<Product> getTopSoldItemsOfAllTime(Pageable pageable);

    @Query("select co.product, sum(co.quantity) as total_quantity_sold " +
        "from CustomerOrder co " +
        "where co.orderDateTime between :startDate and :endDate " +
        "group by co.product " +
        "order by total_quantity_sold desc")
    Page<Product> getTopSoldItemsBasedOnSalesByTimeRange(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
}
