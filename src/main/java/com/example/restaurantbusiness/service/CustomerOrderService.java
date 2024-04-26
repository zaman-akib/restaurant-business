package com.example.restaurantbusiness.service;

import com.example.restaurantbusiness.entity.CustomerOrder;
import com.example.restaurantbusiness.entity.Product;
import com.example.restaurantbusiness.repository.CustomerOrderRepository;

import java.time.YearMonth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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

    public List<Product> getTopSoldItemsOfAllTimeBasedOnTotalSaleAmount(Integer numberOfItems) {
        Pageable pageable = PageRequest.of(0, numberOfItems);
        return customerOrderRepository.getTopSoldItemsOfAllTime(pageable)
            .stream().toList();
    }

    public List<Product> getTopSoldItemsBasedOnSalesOfAMonth(Integer numberOfItems, YearMonth yearMonth) {
        Pageable pageable = PageRequest.of(0, numberOfItems);
        LocalDateTime firstDateOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime lastDateOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return customerOrderRepository.getTopSoldItemsBasedOnSalesByTimeRange(pageable, firstDateOfMonth, lastDateOfMonth)
            .stream().toList();
    }

    public String getMaxSaleDay(LocalDate startDate, LocalDate endDate) {
        log.debug("Calling findMaxSaleDay method from customerOrderRepository");
        List<Object[]> result = customerOrderRepository
            .findMaxSaleDay(startDate.atStartOfDay(),
                endDate.atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59));
        log.debug("Got results from findMaxSaleDay method from customerOrderRepository");

        if (result != null && !result.isEmpty()) {
            log.debug("Parsing results got from findMaxSaleDay method");
            Object[] maxSaleDay = result.get(0);
            LocalDate maxSaleDate = LocalDate.parse(maxSaleDay[0].toString());
            BigDecimal totalSaleAmount = (BigDecimal) maxSaleDay[1];
            log.debug("Parsed results got from findMaxSaleDay method");

            return "Max Sale Day: " + maxSaleDate + ", Total Sale Amount: " + totalSaleAmount;
        }

        return "No data found for the given time range.";
    }
}
