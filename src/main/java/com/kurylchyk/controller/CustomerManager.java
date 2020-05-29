package com.kurylchyk.controller;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;

public class CustomerManager {

    static Customer createCustomer(String name, String surname, String phoneNumber) {

       Customer customer = Customer.newCustomer()
               .setName(name)
               .setSurname(surname)
               .setPhoneNumber(phoneNumber)
               .buildCustomer();

       return customer;
    }

}
