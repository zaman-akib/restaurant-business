package com.example.restaurantbusiness.controller;

import com.example.restaurantbusiness.entity.CustomerOrder;
import com.example.restaurantbusiness.entity.Product;
import com.example.restaurantbusiness.service.CustomerOrderService;

import java.time.LocalDateTime;
import java.time.YearMonth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/customer-orders")
public class CustomOrderController {
    private final CustomerOrderService customerOrderService;

    public CustomOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/current-day")
    public ResponseEntity<List<CustomerOrder>> getOrdersForCurrentDay() {
        log.info("Getting all order for current day");
        List<CustomerOrder> currentDayOrders = customerOrderService.getOrdersForCurrentDay();
        log.info("Successfully retrieved all order for current day");
        return ResponseEntity.ok(currentDayOrders);
    }

    @GetMapping("/total-sale-amount/current-day")
    public ResponseEntity<BigDecimal> getTotalSaleAmountForCurrentDay() {
        log.info("Getting total sale amount for current day");
        BigDecimal totalSaleAmount = customerOrderService.getTotalSaleAmountForCurrentDay();
        log.info("Successfully retrieved total sale amount for current day");
        return ResponseEntity.ok(totalSaleAmount);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerOrder>> getOrdersByCustomerId(@PathVariable Long customerId) {
        log.info("Getting all orders for customer ID: " + customerId);
        List<CustomerOrder> customerOrders = customerOrderService.getOrdersByCustomerId(customerId);
        log.info("Successfully retrieved all orders for customer ID: " + customerId);
        return ResponseEntity.ok(customerOrders);
    }

    @GetMapping("/max-sale-day")
    public ResponseEntity<String> getMaxSaleDay(
        @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        log.info("Getting max sale day between " + startDate + " and " + endDate);
        LocalDate startLocalDate, endLocalDate;
        try {
            startLocalDate = LocalDate.parse(startDate);
            endLocalDate = LocalDate.parse(endDate);
        } catch (Exception e) {
            log.error("Date format is invalid. Provided Start date: " + startDate + " and End date: " + endDate);
            return ResponseEntity.unprocessableEntity().body("Invalid date format. Valid date format is: YYYY-MM-DD");
        }
        log.info("Successfully retrieved max sale day between " + startDate + " and " + endDate);
        return ResponseEntity.ok(customerOrderService.getMaxSaleDay(startLocalDate, endLocalDate));
    }

    @GetMapping("/top-5-sold-items-of-all-time")
    public ResponseEntity<List<Product>> getTopFiveSoldItemsOfAllTime() {
        log.info("Getting top 5 selling items of all time");
        List<Product> customerOrders = customerOrderService
            .getTopSoldItemsOfAllTimeBasedOnTotalSaleAmount(5);
        log.info("Successfully retrieved top 5 selling items of all time");
        return ResponseEntity.ok(customerOrders);
    }

    @GetMapping("/top-5-sold-items-of-last-month")
    public ResponseEntity<List<Product>> getTopFiveSoldItemsOfLastMonth() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        YearMonth lastMonth = YearMonth.from(currentDateTime.minusMonths(1));

        log.info("Getting top 5 selling items of last month");
        List<Product> customerOrders = customerOrderService
            .getTopSoldItemsBasedOnSalesOfAMonth(5, lastMonth);
        log.info("Successfully retrieved top 5 selling items of last month");
        return ResponseEntity.ok(customerOrders);
    }
}
