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
    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderService(CustomerOrderRepository orderRepository) {
        this.customerOrderRepository = orderRepository;
    }

    public List<CustomerOrder> getOrdersForCurrentDay() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusHours(23).plusMinutes(59).plusSeconds(59);
        return customerOrderRepository.findByOrderDateTimeBetween(startOfDay, endOfDay);
    }

    public BigDecimal getTotalSaleAmountForCurrentDay() {
        List<CustomerOrder> currentDayOrders = getOrdersForCurrentDay();

        return currentDayOrders.stream()
            .map(CustomerOrder::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CustomerOrder> getOrdersByCustomerId(Long customerId) {
        return customerOrderRepository.findByCustomerId(customerId);
    }
}
