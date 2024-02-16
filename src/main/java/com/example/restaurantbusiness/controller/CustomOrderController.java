package com.example.restaurantbusiness.controller;

import com.example.restaurantbusiness.entity.CustomerOrder;
import com.example.restaurantbusiness.service.CustomerOrderService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
