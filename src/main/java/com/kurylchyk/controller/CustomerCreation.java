package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.dao.CustomerDao;
import com.kurylchyk.model.vehicles.Vehicle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/customerCreation")
public class CustomerCreation extends HttpServlet {

    private CustomerDao customerDao;

    @Override
    public void init() throws ServletException {
        customerDao = new CustomerDao();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phone_number");
        System.out.println("In customer creation!");

        if(!validateNameAndSurname(name,surname)){

        }
        if(!validatePhoneNumber(phoneNumber)){

        } else {

            int id = checkCustomerIsInDB(phoneNumber);
            Customer customer;
            if (id < 0) {
                id = addCustomerToDB(getCreatedCustomer(name, surname, phoneNumber));

            }
            customer = customerDao.select(id);
            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);

            System.out.println("Forward to parkingTicketCreation");
            req.getRequestDispatcher("parkingTicketCreation").forward(req, resp);
        }
    }
    private boolean validateNameAndSurname(String name, String surname) {

        String regex = "^\\D*$";
        return name.matches(regex) && surname.matches(regex);
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.charAt(0)=='0' && phoneNumber.length()==10 && phoneNumber.matches("[0-9]+");
    }
    private Integer checkCustomerIsInDB(String phoneNumber) {

        return customerDao.selectIdByPhoneNumber(phoneNumber);
    }

    private Customer getCreatedCustomer(String name, String surname, String phoneNumber) {

        return new Customer(name, surname, phoneNumber);
    }

    private Integer addCustomerToDB(Customer customer){
       return customerDao.insert(customer);

    }


}
