package com.kurylchyk.controller;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.impl.ServiceFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/*")
public class CustomerServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CustomerServlet.class);
    private CustomerService customerService = ServiceFacade.forCustomer();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String command = req.getPathInfo();
            switch (command) {
                case "/create":
                    doCreate(req, resp);
                    break;
                case "/update":
                    doUpdate(req, resp);
                    break;
                case "/show":
                    doShow(req, resp);
                    break;
                case "/edit":
                    doEdit(req, resp);
                    break;
                case "/get":
                    doGetCustomer(req, resp);
                    break;
                case "/form":
                    showRegistrationForm(req, resp);
                    break;
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    protected void showRegistrationForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/customerRegistration.jsp").forward(req, resp);
    }

    protected void doCreate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Customer customer = null;
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");
        try {
            customer = customerService.create(name, surname, phoneNumber);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }

        req.getSession().setAttribute("customer", customer);
        resp.sendRedirect("/parkingTicket/create");
    }


    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer customerID = Integer.parseInt(req.getParameter("customerID"));
        try {
            Customer customer = customerService.getFromDB(customerID);
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/customerRegistration.jsp").forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }
    }

    protected void doShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Customer> allCustomers = customerService.getAll();
            req.setAttribute("allCustomers", allCustomers);
            req.getRequestDispatcher("/showAllCustomers.jsp").forward(req, resp);
        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }
    }

    protected void doGetCustomer(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String phoneNumber = req.getParameter("phoneNumber");
        try {
            Customer customer = customerService.getFromDB(phoneNumber);
            req.getRequestDispatcher("/parkingTicket/showByCustomer?customerID=" + customer.getCustomerID()).forward(req, resp);
        } catch (ParkingSystemException exception) {
            req.setAttribute("NotFound", exception);
            logger.error(exception);
            req.getRequestDispatcher("/searchPage.jsp").forward(req, resp);
        }
    }

    protected void doUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer customerID = Integer.parseInt(req.getParameter("customerID"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");


        try {
            Customer customer = Customer.newCustomer().setCustomerID(customerID)
                    .setName(name).setSurname(surname).setPhoneNumber(phoneNumber).buildCustomer();
            customerService.update(customer);
            req.setAttribute("customerID", customerID);
            req.getRequestDispatcher("/parkingTicket/showByCustomer").forward(req, resp);

        } catch (ParkingSystemException exception) {
            logger.error(exception);
        }
    }


}
