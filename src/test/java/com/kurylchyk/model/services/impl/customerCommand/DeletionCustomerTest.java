package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.domain.customer.Customer;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class DeletionCustomerTest {


    @Mock
    CustomerDAO customerDAO = mock(CustomerDAO.class);

    @InjectMocks
    DeleteCustomerCommand command;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private Customer customer;

    @BeforeEach
    public void init() {

        customer = Customer.newCustomer()
                .setCustomerID(12)
                .setName("Name")
                .setSurname("Surname")
                .setPhoneNumber("+3809310922589")
                .buildCustomer();
    }

    @Test
    @DisplayName("Should delete customer from database")
    public void shouldUpdateCustomer() {

        command = new DeleteCustomerCommand(customer,customerDAO);
        doAnswer(
                (cus)->{ assertSame(cus.getArguments()[0],customer);
                    return null; }
        ).when(customerDAO).delete(customer);

        verify(customerDAO).delete(customer);

    }

}
