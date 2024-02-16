package com.example.restaurantbusiness.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.repository.CustomerRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllRegisteredCustomersTest() {
        List<Customer> registeredCustomers = getTestCustomers();

        when(customerRepository.findAllCustomerBasedOnRegistration(true)).thenReturn(registeredCustomers);

        List<Customer> result = customerService.getAllRegisteredCustomers();

        verify(customerRepository, times(1)).findAllCustomerBasedOnRegistration(true);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(result.get(0), registeredCustomers.get(0));
        assertEquals(result.get(1), registeredCustomers.get(1));
    }

    private static List<Customer> getTestCustomers() {
        Customer registeredCustomer1 = new Customer();
        registeredCustomer1.setId(1L);
        registeredCustomer1.setName("John Doe");
        registeredCustomer1.setPhone("01024742451");
        registeredCustomer1.setIsRegistered(true);

        Customer registeredCustomer2 = new Customer();
        registeredCustomer2.setId(2L);
        registeredCustomer2.setName("Jane Doe2");
        registeredCustomer2.setPhone("01024742455");
        registeredCustomer2.setIsRegistered(true);

        return List.of(registeredCustomer1, registeredCustomer2);
    }
}
