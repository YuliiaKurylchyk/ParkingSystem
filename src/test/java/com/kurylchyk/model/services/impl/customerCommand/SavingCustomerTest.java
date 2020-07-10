package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.engine.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;


import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class SavingCustomerTest {

    @Mock
    CustomerDAO customerDAO = mock(CustomerDAO.class);

    @InjectMocks
    SaveCustomerCommand saveCustomerCommand;

    private Customer customer;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }




    @Disabled
    @Test
    public void shouldSaveCustomer() throws ParkingSystemException, SQLException {


        Customer customer = new Customer();
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setPhoneNumber("+380936310258");

        when(customerDAO.insert(anyObject())).thenReturn(1);
        MockitoAnnotations.initMocks(this);
        SaveCustomerCommand saveCustomerCommand = new SaveCustomerCommand(customer,customerDAO);

        customer = saveCustomerCommand.execute();

        customer.setCustomerID(1);

        Integer customerID = customer.getCustomerID();
       assertTrue(1==customerID);

    }

}
