package com.kurylchyk.controller;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.CustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CustomerServletTest {

    @Mock
     HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    HttpSession session;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerServlet customerServlet;

    private static Customer customer;
    @BeforeAll
    public static void initAll(){

        customer = Customer.newCustomer().setCustomerID(1).setName("Name")
                .setSurname("Surname").setPhoneNumber("+380961092586").buildCustomer();
    }

    @BeforeEach
    public void init() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request,response);
        doNothing().when(response).sendRedirect(anyString());
        doNothing().when(session).setAttribute("customer",customer);
        doNothing().when(request).setAttribute("customer",customer);
        when(request.getSession()).thenReturn(session);

        when(request.getParameter("customerID")).thenReturn(String.valueOf(customer.getCustomerID()));
        when(request.getParameter("name")).thenReturn(customer.getName());
        when(request.getParameter("surname")).thenReturn(customer.getSurname());
        when(request.getParameter("phoneNumber")).thenReturn(customer.getPhoneNumber());
    }

    @Test
    @DisplayName("Creating customer in servlet")
    public void shouldCreateCustomer() throws ParkingSystemException, IOException {

        when(customerService.create(customer.getName(),customer.getSurname(),customer.getPhoneNumber())).thenReturn(customer);

        customerServlet.doCreate(request,response);

        verify(request).getParameter("name");
        verify(request).getParameter("surname");
        verify(request).getParameter("phoneNumber");
        verify(customerService).create(customer.getName(),customer.getSurname(),customer.getPhoneNumber());
    }

    @Test
    @DisplayName("Edit customer in servlet")
    public void shouldEditCustomer() throws ParkingSystemException, IOException, ServletException {


        when(customerService.getFromDB(customer.getCustomerID())).thenReturn(customer);
        customerServlet.doEdit(request,response);

        verify(request).getParameter("customerID");
        verify(customerService).getFromDB(customer.getCustomerID());
    }


    @Test
    @DisplayName("Should get customer")
    public void shouldGetCustomer() throws ParkingSystemException, ServletException,
            IOException {
        when(customerService.getFromDB(customer.getPhoneNumber())).thenReturn(customer);
        customerServlet.doGetCustomer(request,response);

        verify(request).getParameter("phoneNumber");
        verify(customerService).getFromDB(customer.getPhoneNumber());
    }

    @Test
    @DisplayName("Should NOT get customer")
    public void shouldNotGetVehicle() throws ParkingSystemException, ServletException,
            IOException {
        String wrongPhoneNumber = "+38096412589";
        when(request.getParameter("phoneNumber")).thenReturn(wrongPhoneNumber);
        when(customerService.getFromDB(wrongPhoneNumber)).thenThrow(NoSuchCustomerFoundException.class);
        customerServlet.doGetCustomer(request, response);

        verify(request).setAttribute(eq("NotFound"), any(Exception.class));

    }

    @Test
    @DisplayName("Should update customer")
    public void shouldUpdateCustomer() throws ParkingSystemException, ServletException, IOException {

        doNothing().when(customerService).update(any(Customer.class));

        customerServlet.doUpdate(request,response);

        verify(request).getParameter("customerID");
        verify(request).getParameter("name");
        verify(request).getParameter("surname");
        verify(request).getParameter("phoneNumber");
        verify(customerService).update(any(Customer.class));
    }

    @Test
    @DisplayName("Should get all customers")
    public void shouldGetAll() throws ParkingSystemException, ServletException, IOException {
        List<Customer> allCustomers = new ArrayList<>(Arrays.asList(customer,customer));
        when(customerService.getAll()).thenReturn(allCustomers);

        customerServlet.doShow(request,response);
        verify(customerService).getAll();
        verify(request,never()).getParameter(anyString());
    }

    @Test
    @DisplayName("Should doGet and call doUpdate")
    public void  shouldDoGet() throws ParkingSystemException {

        when(request.getPathInfo()).thenReturn("/update");
        doNothing().when(customerService).update(any(Customer.class));

        customerServlet.doGet(request,response);

        verify(request).getPathInfo();
        verify(request).getParameter("customerID");
        verify(request).getParameter("name");
        verify(request).getParameter("surname");
        verify(request).getParameter("phoneNumber");
        verify(customerService).update(any(Customer.class));

    }

}
