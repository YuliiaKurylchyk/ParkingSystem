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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DisplayName("Deletion of customer test")
public class DeletionCustomerTest {


    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private DeleteCustomerCommand command;


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
    @DisplayName("Should delete customer from database")
    public void shouldUpdateCustomer() throws ParkingSystemException {

        command = new DeleteCustomerCommand(customer, customerDAO);
        doAnswer(
                (args) -> {
                    if (args.getArguments().length == 1 && args.getArguments()[0] != null) {
                        Customer cus = (Customer) args.getArguments()[0];
                        assertSame(cus, customer);
                    }
                    return null;
                }
        ).when(customerDAO).delete(customer);

        command.execute();
        verify(customerDAO).delete(customer);

    }

}
