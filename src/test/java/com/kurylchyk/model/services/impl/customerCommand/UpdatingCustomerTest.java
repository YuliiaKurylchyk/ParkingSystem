package com.kurylchyk.model.services.impl.customerCommand;


import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdatingCustomerTest {


    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private UpdateCustomerCommand updateCustomerCommand;


    private Customer customer;

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);
        customer = Customer.newCustomer()
                .setCustomerID(12)
                .setName("Name")
                .setSurname("Surname")
                .setPhoneNumber("+3809310922589")
                .buildCustomer();
    }

    @Test
    @DisplayName("Should update customer")
    public void shouldUpdateCustomer() throws ParkingSystemException {

        updateCustomerCommand = new UpdateCustomerCommand(customer,customerDAO);
        doAnswer(
                (cus)->{ assertSame(cus.getArguments()[0],customer);
                return null; }
        ).when(customerDAO).update(customer,customer.getCustomerID());
        updateCustomerCommand.execute();

        verify(customerDAO).update(customer,customer.getCustomerID());
    }

}
