package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GettingAllCustomersTest {


    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private GetAllCustomersCommand command;

    private List<Customer> allCustomers;

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);
        allCustomers = new ArrayList<>(
                Arrays.asList(
                        Customer.newCustomer().setCustomerID(1).setName("Name").setSurname("Surname").setPhoneNumber("+380931092580").buildCustomer(),
                        Customer.newCustomer().setCustomerID(1).setName("Name1").setSurname("Surname1").setPhoneNumber("+380931092581").buildCustomer(),
                        Customer.newCustomer().setCustomerID(1).setName("Name2").setSurname("Surname3").setPhoneNumber("+380931092582").buildCustomer()
                        )
        );

        when(customerDAO.selectAll()).thenReturn(allCustomers);
    }

    @Test
    @DisplayName("Should return all customers")
    public void shouldReturnAllCustomers() throws ParkingSystemException {

        List<Customer> selectedCustomers = command.execute();

        assertAll(
                ()->  assertNotNull(selectedCustomers),
                ()->assertEquals(allCustomers,selectedCustomers)

        );

        verify(customerDAO).selectAll();
    }

}
