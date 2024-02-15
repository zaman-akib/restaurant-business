package com.example.restaurantbusiness.service;

import com.example.restaurantbusiness.entity.CustomerOrder;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerOrderServiceTest {
    @Autowired
    private CustomerOrderService customerOrderService;

    @Test
    void saveOrderTest() {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderDateTime(LocalDateTime.now());

        CustomerOrder newCustomerOrder = customerOrderService.save(customerOrder);

        Assertions.assertEquals(newCustomerOrder.getId(), customerOrder.getId());
    }

    @Test
    void getOrdersForCurrentDayTest() {
        List<CustomerOrder> currentDayCustomerOrders = customerOrderService.getOrdersForCurrentDay();
        Assertions.assertFalse(currentDayCustomerOrders.isEmpty());
    }
}
