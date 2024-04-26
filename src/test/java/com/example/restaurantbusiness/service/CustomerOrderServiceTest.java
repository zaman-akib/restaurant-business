package com.example.restaurantbusiness.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.entity.CustomerOrder;
import com.example.restaurantbusiness.entity.Product;
import com.example.restaurantbusiness.repository.CustomerOrderRepository;

import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class CustomerOrderServiceTest {
    @InjectMocks
    private CustomerOrderService customerOrderService;
    @Mock
    private CustomerOrderRepository customerOrderRepository;
    private List<CustomerOrder> customerOrders;
    private List<Product> mockProducts;

    @BeforeEach
    void setup() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("P1");
        product1.setPrice(BigDecimal.valueOf(100.0));
        product1.setStockAvailable(5);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("P2");
        product2.setPrice(BigDecimal.valueOf(200.0));
        product2.setStockAvailable(12);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setPhone("01073483632");
        customer.setIsRegistered(true);

        CustomerOrder order1 = new CustomerOrder();
        order1.setId(1L);
        order1.setOrderDateTime(currentDateTime.minusHours(1));
        order1.setCustomer(customer);
        order1.setPrice(BigDecimal.valueOf(200.0));
        order1.setProduct(product2);
        order1.setQuantity(5);

        CustomerOrder order2 = new CustomerOrder();
        order2.setId(2L);
        order2.setOrderDateTime(currentDateTime);
        order2.setCustomer(customer);
        order2.setPrice(BigDecimal.valueOf(100.0));
        order2.setProduct(product1);
        order2.setQuantity(2);

        CustomerOrder order3 = new CustomerOrder();
        order3.setId(3L);
        order3.setOrderDateTime(currentDateTime.plusHours(1));
        order3.setCustomer(customer);
        order3.setPrice(BigDecimal.valueOf(230.0));
        order3.setProduct(product1);
        order3.setQuantity(2);

        customerOrders = List.of(order1, order2, order3);



        mockProducts = Arrays.asList(product1, product2);
    }

    @Test
    void getOrdersForCurrentDayTest() {
        when(customerOrderRepository.findByOrderDateTimeBetween(any(), any())).thenReturn(customerOrders);

        List<CustomerOrder> ordersForCurrentDay = customerOrderService.getOrdersForCurrentDay();

        verify(customerOrderRepository, times(1)).findByOrderDateTimeBetween(any(), any());

        assertNotNull(ordersForCurrentDay);
        assertEquals(3, ordersForCurrentDay.size()); // Assuming three orders for the current day
        assertEquals(customerOrders.get(0).getCustomer(), ordersForCurrentDay.get(0).getCustomer());
        assertEquals(customerOrders.get(1).getCustomer(), ordersForCurrentDay.get(1).getCustomer());
        assertEquals(customerOrders.get(2).getCustomer(), ordersForCurrentDay.get(2).getCustomer());
    }

    @Test
    void getTotalSaleAmountForCurrentDayTest() {
        when(customerOrderRepository.findByOrderDateTimeBetween(any(), any())).thenReturn(customerOrders);

        BigDecimal totalSaleAmount = customerOrderService.getTotalSaleAmountForCurrentDay();

        verify(customerOrderRepository, times(1)).findByOrderDateTimeBetween(any(), any());

        assertNotNull(totalSaleAmount);
        assertEquals(BigDecimal.valueOf(530.0), totalSaleAmount); // 200.0 + 100.0 + 230.0
    }

    @Test
    void getOrdersByCustomerIdTest() {
        Long customerId = 1L;
        
        when(customerOrderRepository.findByCustomerId(customerId)).thenReturn(customerOrders);

        List<CustomerOrder> result = customerOrderService.getOrdersByCustomerId(customerId);

        verify(customerOrderRepository, times(1)).findByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(customerOrders.get(0), result.get(0));
        assertEquals(customerOrders.get(1), result.get(1));
        assertEquals(customerOrders.get(2), result.get(2));
    }

    @Test
    void getMaxSaleDayTest() {
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        
        Object[] maxSaleDayResult = new Object[]{LocalDate.parse("2023-06-15"), BigDecimal.valueOf(500.0)};
        List<Object[]> result = Collections.singletonList(maxSaleDayResult);
        
        when(customerOrderRepository.findMaxSaleDay(any(), any())).thenReturn(result);

        String maxSaleDayInfo = customerOrderService.getMaxSaleDay(startDate, endDate);

        verify(customerOrderRepository, times(1)).findMaxSaleDay(
            startDate.atStartOfDay(), endDate.atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59));

        assertNotNull(maxSaleDayInfo);
        assertEquals("Max Sale Day: 2023-06-15, Total Sale Amount: 500.0", maxSaleDayInfo);
    }

    @Test
    void getMaxSaleDayNoDataTest() {
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");

        List<Object[]> result = List.of();

        when(customerOrderRepository.findMaxSaleDay(any(), any())).thenReturn(result);

        String maxSaleDayInfo = customerOrderService.getMaxSaleDay(startDate, endDate);

        verify(customerOrderRepository, times(1)).findMaxSaleDay(
            startDate.atStartOfDay(), endDate.atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59));

        assertNotNull(maxSaleDayInfo);
        assertEquals("No data found for the given time range.", maxSaleDayInfo);
    }

    @Test
    public void getTopSoldItemsOfAllTimeBasedOnTotalSaleAmountTest() {
        Pageable pageable = PageRequest.of(0, 5);
        when(customerOrderRepository.getTopSoldItemsOfAllTime(pageable)).thenReturn(
            new PageImpl<>(mockProducts));

        int numberOfItems = 5;
        List<Product> result = customerOrderService.getTopSoldItemsOfAllTimeBasedOnTotalSaleAmount(numberOfItems);

        assertEquals(mockProducts.size(), result.size());
        for (int i = 0; i < mockProducts.size(); i++) {
            assertEquals(mockProducts.get(i).getName(), result.get(i).getName());
        }

        verify(customerOrderRepository, times(1)).getTopSoldItemsOfAllTime(pageable);
    }

    @Test
    public void getTopSoldItemsBasedOnSalesOfAMonthTest() {
        Pageable pageable = PageRequest.of(0, 5);
        LocalDateTime firstDateOfMonth = YearMonth.of(2022, 3).atDay(1).atStartOfDay();
        LocalDateTime lastDateOfMonth = YearMonth.of(2022, 3).atEndOfMonth().atTime(23, 59, 59);
        when(customerOrderRepository.getTopSoldItemsBasedOnSalesByTimeRange(pageable, firstDateOfMonth, lastDateOfMonth))
            .thenReturn(new PageImpl<>(mockProducts));

        int numberOfItems = 5;
        YearMonth yearMonth = YearMonth.of(2022, 3);
        List<Product> result = customerOrderService.getTopSoldItemsBasedOnSalesOfAMonth(numberOfItems, yearMonth);

        assertEquals(mockProducts.size(), result.size());
        for (int i = 0; i < mockProducts.size(); i++) {
            assertEquals(mockProducts.get(i).getName(), result.get(i).getName());
        }

        verify(customerOrderRepository, times(1)).getTopSoldItemsBasedOnSalesByTimeRange(pageable, firstDateOfMonth, lastDateOfMonth);
    }
}
