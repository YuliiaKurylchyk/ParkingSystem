package com.kurylchyk.model.dao;

import com.kurylchyk.model.domain.customer.Customer;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import javax.sql.DataSource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CustomerDAOTest {

    @Mock
    Connector connector;
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    Statement mockStatement;
    @Mock
    ResultSet mockResultSet;

    @InjectMocks
    CustomerDAO customerDAO;

    private Customer customerWithID;
    private Customer customerWithoutID;


    @BeforeEach
    public void init() throws SQLException {
        MockitoAnnotations.initMocks(this);
        customerWithID = Customer.newCustomer().setCustomerID(1).setName("Name").setSurname("Surname").setPhoneNumber("+380931092589").buildCustomer();
       customerWithoutID = Customer.newCustomer().setName("Name").setSurname("Surname").setPhoneNumber("+380931269874").buildCustomer();

        when(connector.getDataSource()).thenReturn(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
        doNothing().when(mockPreparedStatement).setInt(anyInt(),anyInt());
        when(mockPreparedStatement.execute()).thenReturn(true);
        when(mockResultSet.getInt("customer_id")).thenReturn(customerWithID.getCustomerID());
        when(mockResultSet.getString("name")).thenReturn(customerWithID.getName());
        when(mockResultSet.getString("surname")).thenReturn(customerWithID.getSurname());
        when(mockResultSet.getString("phone_number")).thenReturn(customerWithID.getPhoneNumber());
    }


    @Test
    @DisplayName("Should select customer from database by id")
    public void shouldSelectCustomer() throws SQLException {

        doNothing().when(mockPreparedStatement).setInt(1,customerWithID.getCustomerID());
        when(mockResultSet.next()).thenReturn(true);
       Customer selectedCustomer =  customerDAO.select(customerWithID.getCustomerID()).get();

       assertAll(
               ()->assertNotNull(selectedCustomer),
               ()->  assertEquals(customerWithID,selectedCustomer));

       InOrder inOrder = Mockito.inOrder(mockResultSet);
       inOrder.verify(mockResultSet).next();
       inOrder.verify(mockResultSet).getInt("customer_id");
       inOrder.verify(mockResultSet).getString("name");
       inOrder.verify(mockResultSet).getString("surname");
       inOrder.verify(mockResultSet).getString("phone_number");

    }

    @Test
    @DisplayName("Should select customer from database by phone number")
    public void shouldSelectCustomerByPhoneNumber() throws SQLException {

        doNothing().when(mockPreparedStatement).setString(1,customerWithID.getPhoneNumber());
        when(mockResultSet.next()).thenReturn(true);
        Customer selectedCustomer =  customerDAO.select(customerWithID.getPhoneNumber()).get();

        assertAll(
                ()->assertNotNull(selectedCustomer),
                ()->  assertEquals(customerWithID,selectedCustomer));

        InOrder inOrder = Mockito.inOrder(mockResultSet);
        inOrder.verify(mockResultSet).next();
        inOrder.verify(mockResultSet).getInt("customer_id");
        inOrder.verify(mockResultSet).getString("name");
        inOrder.verify(mockResultSet).getString("surname");
        inOrder.verify(mockResultSet).getString("phone_number");

    }


    @Test
    @DisplayName("Should select all customers")
    public void shouldSelectAll() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        List<Customer> allCustomers  = customerDAO.selectAll();

        assertAll(
                ()-> assertNotNull(allCustomers),
                ()-> assertTrue(allCustomers.size() ==1)
        );
    }

    @Test
    @DisplayName("Should insert  customer from database")
    public void shouldInsertToDatabase() throws SQLException {

        Integer expectedID = 14;
        when(mockConnection.prepareStatement(anyString(),eq(1))).thenReturn(mockPreparedStatement);
        doNothing().when(mockPreparedStatement).setInt(1,customerWithID.getCustomerID());
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(expectedID);


        Integer generatedID=  customerDAO.insert(customerWithoutID);
        assertAll(
                ()->assertNotNull(generatedID),
                ()->  assertEquals(generatedID,expectedID));

        verify(mockPreparedStatement).getGeneratedKeys();

    }


    @Test
    @DisplayName("Should update customer")
    public void shouldUpdateCustomer(){

        customerDAO.update(customerWithoutID,customerWithID.getCustomerID());

    }

    @Test
    @DisplayName("Should delete customer")
    public void shouldDeleteCustomer(){

        customerDAO.delete(customerWithID);
    }

    @Nested
    @DisplayName("Testing of customer is present in db")
    class CheckingCustomerIsPresent {
        @Test
        @DisplayName("Should state that customer is present")
        public void shouldBePresentByID() throws SQLException {
            doNothing().when(mockPreparedStatement).setInt(1, customerWithID.getCustomerID());
            when(mockResultSet.next()).thenReturn(true);
            Boolean result = customerDAO.isPresent(customerWithID.getCustomerID());

            assertTrue(result);
        }

        @Test
        @DisplayName("Should state that customer is NOT  present")
        public void shouldBeNotPresentByID() throws SQLException {
            Integer wrongCustomerID = 15;
            doNothing().when(mockPreparedStatement).setInt(1, wrongCustomerID);
            when(mockResultSet.next()).thenReturn(false);
            Boolean result = customerDAO.isPresent(wrongCustomerID);

            assertFalse(result);
        }

        @Test
        @DisplayName("Should state that customer is present")
        public void shouldBePresentByPhoneNumber() throws SQLException {
            doNothing().when(mockPreparedStatement).setString(1,customerWithID.getPhoneNumber());
            when(mockResultSet.next()).thenReturn(true);
            Boolean result = customerDAO.isPresent(customerWithID.getPhoneNumber());
            assertTrue(result);

        }


        @Test
        @DisplayName("Should state that customer is NOT  present")
        public void shouldBeNotPresentByPhoneNumber() throws SQLException {
            String wrongPhoneNumber = "+380964596321";
            doNothing().when(mockPreparedStatement).setString(1, wrongPhoneNumber);
            when(mockResultSet.next()).thenReturn(false);
            Boolean result = customerDAO.isPresent(wrongPhoneNumber);

            assertFalse(result);
        }
    }

    @AfterEach
    public void verifyCalls() throws SQLException {

        verify(connector).getDataSource();
        verify(mockDataSource).getConnection();

    }


}
