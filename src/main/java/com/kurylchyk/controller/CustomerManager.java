package com.kurylchyk.controller;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerManager {
    private static CustomerDAO customerDAO = new CustomerDAO();


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

       Customer customer = Customer.newCustomer()
               .setName(name)
               .setSurname(surname)
               .setPhoneNumber(phoneNumber)
               .buildCustomer();

       return customer;
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
