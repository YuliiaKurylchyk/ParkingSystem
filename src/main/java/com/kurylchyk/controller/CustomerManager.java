package com.kurylchyk.controller;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerManager {
    private static CustomerDAO customerDAO = new CustomerDAO();

   static void validateCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean failed = false;
        RequestDispatcher requestDispatcher = null;

        String regex = "^\\D*$";
        if (req.getParameter("name").equals("") || req.getParameter("surname").equals("") || !req.getParameter("name").matches(regex) || !req.getParameter("surname").matches(regex)) {

            failed = true;
            req.setAttribute("badData", "Bad format of name or surname. Try again");

        }
        String phoneNumber = req.getParameter("phone_number");
        if (phoneNumber.equals("") || !(phoneNumber.charAt(0) == '0' && phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+"))) {
            failed = true;
            req.setAttribute("badPhoneNumber", "Bad format of phone number.It must start with 0. Try again");
        }

        if (failed) {
            requestDispatcher = req.getRequestDispatcher("customerRegistration.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    //зробити тут 2 методи, один котрий перевіряє чи є customer а інший бере id і в Creation це переробити
    static Integer checkCustomerIsInDB(String phoneNumber) {
        try {
            return customerDAO.selectIdByPhoneNumber(phoneNumber);
        } catch (NoSuchCustomerFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    static Customer createCustomer(String name, String surname, String phoneNumber) {

        return new Customer(name, surname, phoneNumber);
    }

    static Customer getCustomerFromDB(Integer id) {
       Customer customer = null;
       try{
           customer = customerDAO.select(id);
       }catch (NoSuchCustomerFoundException ex){
           ex.printStackTrace();
       }
       return customer;
    }

}
