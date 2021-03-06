package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;



@DisplayName("Saving customer to db test")
public class SavingCustomerTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private SaveCustomerCommand saveCustomerCommand;

    private  Customer customer;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        customer = new Customer();
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setPhoneNumber("+380936310258");
    }



    @Test
    @DisplayName("Saving customer test")
    public void shouldSaveCustomer() throws ParkingSystemException {

        Integer expectedCustomerID = 1;
        when(customerDAO.insert(any(Customer.class))).thenReturn(expectedCustomerID);
        saveCustomerCommand = new SaveCustomerCommand(customer,customerDAO);

        Customer savedCustomer = saveCustomerCommand.execute();

        assertAll(
                ()->assertSame(customer,savedCustomer,()->"Returned customer should be the same instance"),
                ()->assertTrue(expectedCustomerID == savedCustomer.getCustomerID(),
                        ()->"Returned customer should have customer id")
        );

        verify(customerDAO).insert(any(Customer.class));
    }

}
