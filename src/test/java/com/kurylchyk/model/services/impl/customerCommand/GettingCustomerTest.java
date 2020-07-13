package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DisplayName("Getting customer test")
public class GettingCustomerTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private GetCustomerCommand command;



    private static Customer customer;
    private static Integer customerID;
    private static String customerPhoneNumber;
    private static Integer notExistedID;
    private static String notExistedPhoneNumber;

    @BeforeAll
    public static void initAll(){
        customerID  =1;
        customerPhoneNumber = "+380963545786";
        notExistedID = 234;
        notExistedPhoneNumber = "+380931025879";

        customer = Customer.newCustomer()
                .setCustomerID(customerID)
                .setName("Name")
                .setSurname("Surname")
                .setPhoneNumber(customerPhoneNumber).buildCustomer();

    }

    @BeforeEach
    public void init(){


        MockitoAnnotations.initMocks(this);

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
        public void shouldNotGetCustomerByID()  {
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
