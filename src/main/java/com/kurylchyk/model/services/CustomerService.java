package com.kurylchyk.model.services;

import com.kurylchyk.model.customer.Customer;

import java.util.List;

//add get All
public interface CustomerService {

    Customer create(String name,String surname,String phoneNumber) throws Exception;

    Customer create(Integer customerID,String name,String surname,String phoneNumber) throws Exception;
    Customer getFromDB(Integer customerID) throws Exception;
    Customer getFromDB(String phoneNumber) throws Exception;
    Customer saveToDB(Customer customer) throws Exception;
    void update(Customer customer) throws Exception;
    List<Customer> getAll() throws Exception;
    //викликати з deleteTicketCommand
    void deleteCompletely(Customer customer) throws Exception;
    boolean isPresent(String phoneNumber) throws Exception;

}
