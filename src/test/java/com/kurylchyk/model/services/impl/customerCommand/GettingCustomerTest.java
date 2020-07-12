package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GettingCustomerTest {


    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private GetCustomerCommand command;


    private Customer customer;
    private Integer customerID;
    private String customerPhoneNumber;
    private Integer notExistedID;
    private String notExistedPhoneNumber;

    @BeforeEach
    public void init(){
        customerID  =1;
        customerPhoneNumber = "+380963545786";
        notExistedID = 234;
        notExistedPhoneNumber = "+380931025879";

        MockitoAnnotations.initMocks(this);
        customer = Customer.newCustomer()
                .setCustomerID(customerID)
                .setName("Name")
                .setSurname("Surname")
                .setPhoneNumber(customerPhoneNumber).buildCustomer();

        when(customerDAO.select(customerID)).thenReturn(Optional.ofNullable(customer));
        when(customerDAO.select(customerPhoneNumber)).thenReturn(Optional.ofNullable(customer));

        when(customerDAO.select(notExistedID)).thenReturn(Optional.ofNullable(null));
        when(customerDAO.select(notExistedPhoneNumber)).thenReturn(Optional.ofNullable(null));

    }

    @Nested
    class GettingByID {
        @Test
        @DisplayName("Should get customer by id")
        public void shouldGetCustomerByID() throws ParkingSystemException {
            command = new GetCustomerCommand(customerID);
            command.setCustomerDAO(customerDAO);

            Customer selectedCustomer = command.execute();

            assertAll(
                    () -> assertNotNull(selectedCustomer),
                    () -> assertEquals(customerID, selectedCustomer.getCustomerID()),
                    () -> assertEquals(selectedCustomer, customer)
            );

            verify(customerDAO).select(customerID);

        }

        @Test
        @DisplayName("Should  NOT get customer by id")
        public void shouldNotGetCustomerByID() throws ParkingSystemException {
            command = new GetCustomerCommand(notExistedID);
            command.setCustomerDAO(customerDAO);

            assertThrows(NoSuchCustomerFoundException.class, () -> command.execute());
            verify(customerDAO).select(notExistedID);

        }
    }


    @Nested
    class GettingByPhoneNumber{
        @Test
        @DisplayName("Should get customer by phone number")
        public void shouldGetCustomerByPhoneNumber() throws ParkingSystemException {
            command = new GetCustomerCommand(customerPhoneNumber);
            command.setCustomerDAO(customerDAO);

            Customer selectedCustomer = command.execute();

            assertAll(
                    () -> assertNotNull(selectedCustomer),
                    () -> assertEquals(customerPhoneNumber, selectedCustomer.getPhoneNumber()),
                    () -> assertEquals(selectedCustomer, customer)
            );

            verify(customerDAO).select(customerPhoneNumber);

        }

        @Test
        @DisplayName("Should  NOT get customer by phone number")
        public void shouldNotGetCustomerByPhoneNumber() {
            command = new GetCustomerCommand(notExistedPhoneNumber);
            command.setCustomerDAO(customerDAO);
            assertThrows(NoSuchCustomerFoundException.class, () -> command.execute());
            verify(customerDAO).select(notExistedPhoneNumber);

        }


    }


}
