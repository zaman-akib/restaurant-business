package com.example.restaurantbusiness.controller;

import com.example.restaurantbusiness.entity.CustomerOrder;
import com.example.restaurantbusiness.service.CustomerOrderService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class CustomOrderController {
    private final CustomerOrderService customerOrderService;

    public CustomOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/current-day")
    public List<CustomerOrder> getOrdersForCurrentDay() {
        return customerOrderService.getOrdersForCurrentDay();
    }
}
