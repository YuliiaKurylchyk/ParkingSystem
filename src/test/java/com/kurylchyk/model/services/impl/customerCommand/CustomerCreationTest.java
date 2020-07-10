package com.kurylchyk.model.services.impl.customerCommand;


import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Creation of customer")
public class CustomerCreationTest {

    CreateCustomerCommand createCustomerCommand;
    Integer expectedCustomerID = 12;
    String expectedName = "Name";
    String expectedSurname = "Surname";
    String expectedPhoneNumber = "+380961025478";


    @Test
    @DisplayName("Should create customer without id")
    public void shouldCreateCustomerWithoutID() throws ParkingSystemException {

        Customer expectedCustomer = Customer.newCustomer()
                .setName(expectedName)
                .setSurname(expectedSurname)
                .setPhoneNumber(expectedPhoneNumber)
                .buildCustomer();
        createCustomerCommand = new CreateCustomerCommand(expectedName,expectedSurname,expectedPhoneNumber);
        Customer actualCustomer = createCustomerCommand.execute();

        assertAll(
                ()->assertNull(actualCustomer.getCustomerID()),
                ()->assertEquals(expectedCustomer,actualCustomer));
    }


    @Test
    @DisplayName("Should create customer with id")
    public void shouldCreateCustomerWithID() throws ParkingSystemException {

        Customer expectedCustomer = Customer.newCustomer()
                .setCustomerID(expectedCustomerID)
                .setName(expectedName)
                .setSurname(expectedSurname)
                .setPhoneNumber(expectedPhoneNumber)
                .buildCustomer();
        createCustomerCommand = new CreateCustomerCommand(expectedCustomerID,expectedName,expectedSurname,expectedPhoneNumber);
        Customer actualCustomer = createCustomerCommand.execute();

        assertEquals(expectedCustomer,actualCustomer,()->"Should have created "+expectedSurname + " but created "+actualCustomer);
    }
}
