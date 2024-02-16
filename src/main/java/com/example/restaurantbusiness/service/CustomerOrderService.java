package com.example.restaurantbusiness.service;

import com.example.restaurantbusiness.entity.CustomerOrder;
import com.example.restaurantbusiness.repository.CustomerOrderRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService {
    private final CustomerOrderRepository orderRepository;

    public CustomerOrderService(CustomerOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<CustomerOrder> getOrdersForCurrentDay() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusHours(23).plusMinutes(59).plusSeconds(59);
        return orderRepository.findByOrderDateTimeBetween(startOfDay, endOfDay);
    }

    public BigDecimal getTotalSaleAmountForCurrentDay() {
        List<CustomerOrder> currentDayOrders = getOrdersForCurrentDay();

        return currentDayOrders.stream()
            .map(CustomerOrder::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
