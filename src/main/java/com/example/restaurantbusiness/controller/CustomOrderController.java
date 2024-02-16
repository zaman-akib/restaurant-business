package com.example.restaurantbusiness.controller;

import com.example.restaurantbusiness.entity.CustomerOrder;
import com.example.restaurantbusiness.service.CustomerOrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/customer-orders")
public class CustomOrderController {
    private final CustomerOrderService customerOrderService;

    public CustomOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/current-day")
    public ResponseEntity<List<CustomerOrder>> getOrdersForCurrentDay() {
        List<CustomerOrder> currentDayOrders = customerOrderService.getOrdersForCurrentDay();
        return ResponseEntity.ok(currentDayOrders);
    }

    @GetMapping("/total-sale-amount/current-day")
    public ResponseEntity<BigDecimal> getTotalSaleAmountForCurrentDay() {
        BigDecimal totalSaleAmount = customerOrderService.getTotalSaleAmountForCurrentDay();
        return ResponseEntity.ok(totalSaleAmount);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerOrder>> getOrdersByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerOrderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/max-sale-day")
    public ResponseEntity<String> getMaxSaleDay(
        @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        LocalDate startLocalDate, endLocalDate;
        try {
            startLocalDate = LocalDate.parse(startDate);
            endLocalDate = LocalDate.parse(endDate);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body("Invalid date format. Valid date format is: YYYY-MM-DD");
        }
        
        return ResponseEntity.ok(customerOrderService.getMaxSaleDay(startLocalDate, endLocalDate));
    }
}
