package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Checking whether customer is present")
public class CustomerIsPresentTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CheckCustomerIsPresentCommand command;

    private String expectedToBePresentNumber;
    private String expectedNotToBePresentNumber;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        expectedToBePresentNumber = "+380931092456";
        expectedNotToBePresentNumber = "+380989102589";

        when(customerDAO.isPresent(expectedToBePresentNumber)).thenReturn(true);
        when(customerDAO.isPresent(expectedNotToBePresentNumber)).thenReturn(false);
    }

    @Test
    @DisplayName("Should be present by phone number")
    public void shouldBePresent() throws ParkingSystemException {
     command = new CheckCustomerIsPresentCommand(expectedToBePresentNumber,customerDAO);
     Boolean result = command.execute();
     assertTrue(result);
     verify(customerDAO).isPresent(expectedToBePresentNumber);

    }

    @Test
    @DisplayName("Should NOT be present by phone number")
    public void shouldNotBePresent() throws ParkingSystemException {
        command = new CheckCustomerIsPresentCommand(expectedNotToBePresentNumber,customerDAO);
        Boolean result = command.execute();
        assertFalse(result);
        verify(customerDAO).isPresent(expectedNotToBePresentNumber);
    }

}
