package com.kurylchyk.model.services;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import java.util.List;


public interface CustomerService {

    Customer create(String name,String surname,String phoneNumber) throws ParkingSystemException;
    Customer create(Integer customerID,String name,String surname,String phoneNumber) throws ParkingSystemException;
    Customer getFromDB(Integer customerID) throws ParkingSystemException;
    Customer getFromDB(String phoneNumber) throws ParkingSystemException;
    Customer saveToDB(Customer customer) throws ParkingSystemException;
    void update(Customer customer) throws ParkingSystemException;
    List<Customer> getAll() throws ParkingSystemException;
    void deleteCompletely(Customer customer) throws ParkingSystemException;
    boolean isPresent(String phoneNumber) throws ParkingSystemException;

}
