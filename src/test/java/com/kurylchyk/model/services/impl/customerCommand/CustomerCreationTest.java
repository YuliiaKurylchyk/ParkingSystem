package com.kurylchyk.model.services.impl.customerCommand;


import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Creation of customer")
public class CustomerCreationTest {


    private CreateCustomerCommand createCustomerCommand;
    private Integer expectedCustomerID ;
    private String expectedName;
    private String expectedSurname;
    private String expectedPhoneNumber;
    private Customer expectedCustomer;

    @BeforeEach
    public void init(){
        expectedCustomerID  =12;
        expectedName = "Name";
        expectedSurname= "Surname";
        expectedPhoneNumber= "+380961025478";
        expectedCustomer = Customer.newCustomer()
                .setName(expectedName)
                .setSurname(expectedSurname)
                .setPhoneNumber(expectedPhoneNumber)
                .buildCustomer();
    }

    @Test
    @DisplayName("Should create customer without id")
    public void shouldCreateCustomerWithoutID() throws ParkingSystemException {

        createCustomerCommand = new CreateCustomerCommand(expectedName,expectedSurname,expectedPhoneNumber);
        Customer actualCustomer = createCustomerCommand.execute();

        assertAll(
                ()->assertNull(actualCustomer.getCustomerID()),
                ()->assertEquals(expectedCustomer,actualCustomer));
    }


    @Test
    @DisplayName("Should create customer with id")
    public void shouldCreateCustomerWithID() throws ParkingSystemException {

        createCustomerCommand = new CreateCustomerCommand(expectedCustomerID,expectedName,expectedSurname,expectedPhoneNumber);
        Customer actualCustomer = createCustomerCommand.execute();

        assertEquals(expectedCustomer,actualCustomer,()->"Should have created "+expectedSurname + " but created "+actualCustomer);
    }
}
