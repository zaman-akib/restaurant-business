package com.example.restaurantbusiness.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.restaurantbusiness.entity.Customer;
import com.example.restaurantbusiness.entity.Product;
import com.example.restaurantbusiness.repository.CustomerRepository;
import com.example.restaurantbusiness.repository.ProductWishRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductWishRepository productWishRepository;
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

    @Test
    public void getWishListByCustomerIdTest() {
        Integer customerId = 1;

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("P1");
        product1.setStockAvailable(5);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("P2");
        product2.setStockAvailable(12);

        List<Product> mockedProducts = Arrays.asList(product1, product2);

        when(productWishRepository.findAllProductsByCustomerId(customerId))
            .thenReturn(mockedProducts);

        List<Product> result = customerService.getWishListByCustomerId(customerId);

        assertEquals(mockedProducts.size(), result.size());
        for (int i = 0; i < mockedProducts.size(); i++) {
            assertEquals(mockedProducts.get(i).getId(), result.get(i).getId());
            assertEquals(mockedProducts.get(i).getName(), result.get(i).getName());
        }

        verify(productWishRepository, times(1)).findAllProductsByCustomerId(customerId);
    }
}
