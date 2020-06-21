package com.kurylchyk.controller;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.impl.BusinessServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/*")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService = new BusinessServiceFactory().forCustomer();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String command = req.getPathInfo();
        System.out.println("In customerServlet " + command);
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
    }

    protected void showRegistrationForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/customerRegistration.jsp").forward(req, resp);
    }

    protected void doCreate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Customer customer = null;
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");
        try {
            List<String> violations = customerService.validate(name, surname, phoneNumber);
            if (!violations.isEmpty()) {
                setAttributeBack(name, surname, phoneNumber, req);
                req.setAttribute("violations", violations);
                req.getRequestDispatcher("/customerRegistration.jsp").forward(req, resp);
                return;
            }

            if (customerService.isPresent(phoneNumber)) {
                customer = customerService.getFromDB(phoneNumber);
            } else {
                customer = customerService.create(name, surname, phoneNumber);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        req.getSession().setAttribute("customer", customer);
        System.out.println("In customer servlet doCreate " + req.getContextPath());
        req.getRequestDispatcher(req.getContextPath() + "/parkingTicket/create").forward(req, resp);

    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) {
        Integer customerID = Integer.parseInt(req.getParameter("customerID"));
        try{
            Customer customer = customerService.getFromDB(customerID);
            req.setAttribute("customer",customer);
            System.out.println("customer attribute was set");
            req.getRequestDispatcher("/customerRegistration.jsp").forward(req,resp);
        }catch (Exception exception){
            exception.printStackTrace();
        }


    }

    protected void doShow(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Customer> allCustomers = customerService.getAll();
            req.setAttribute("allCustomers", allCustomers);
            req.getRequestDispatcher("/showAllCustomers.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void doGetCustomer(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String phoneNumber = req.getParameter("phoneNumber");
        System.out.println("Phone number "+phoneNumber);
        try {
            Customer customer = customerService.getFromDB(phoneNumber);
            req.getRequestDispatcher("/parkingTicket/showByCustomer?customerID="+customer.getCustomerID()).forward(req, resp);
        } catch (Exception exception) {
            req.setAttribute("NotFound", exception);
            System.out.println("exception in doCustomer");
            req.getRequestDispatcher("/searchPage.jsp").forward(req, resp);
        }
    }

    private void setAttributeBack(String name, String surname, String phoneNumber, HttpServletRequest req) {
        req.setAttribute("name", name);
        req.setAttribute("surname", surname);
        req.setAttribute("phoneNumber", phoneNumber);

    }

    protected void doUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("update customer action");
        Integer customerID =  Integer.parseInt(req.getParameter("customerID"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");
        Customer customer = customerService.create(customerID,name,surname,phoneNumber);
        try {
            customerService.update(customer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        req.setAttribute("customer", customer);
        req.getRequestDispatcher("/parkingTicket/showByCustomer").forward(req, resp);
    }
}
