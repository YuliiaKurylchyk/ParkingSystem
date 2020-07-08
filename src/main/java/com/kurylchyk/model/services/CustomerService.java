package com.kurylchyk.model.services;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;

import java.util.List;

//add get All
public interface CustomerService {

    Customer create(String name,String surname,String phoneNumber) throws ParkingSystemException;
    Customer create(Integer customerID,String name,String surname,String phoneNumber) throws ParkingSystemException;
    Customer getFromDB(Integer customerID) throws Exception;
    Customer getFromDB(String phoneNumber) throws ParkingSystemException;
    Customer saveToDB(Customer customer) throws ParkingSystemException;
    void update(Customer customer) throws ParkingSystemException;
    List<Customer> getAll() throws ParkingSystemException;
    //викликати з deleteTicketCommand
    void deleteCompletely(Customer customer) throws ParkingSystemException;
    boolean isPresent(String phoneNumber) throws ParkingSystemException;

}
