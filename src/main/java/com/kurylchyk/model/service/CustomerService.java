package com.kurylchyk.model.service;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;

import java.util.List;

public class CustomerService implements ServiceAddDelete<Customer,Integer>,ServiceGetUpdate<Customer,Integer>{

    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    public Customer get(Integer customerID) throws NoSuchCustomerFoundException {
        return customerDAO.select(customerID)
                .orElseThrow(()-> new NoSuchCustomerFoundException("No customer with id "+customerID + " found "));
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    public Customer get(String phoneNumber) throws NoSuchCustomerFoundException{
        return customerDAO.select(phoneNumber)
                .orElseThrow(()-> new NoSuchCustomerFoundException("No customer with id phone number"+phoneNumber + " found "));
    }

    @Override
    public Integer add(Customer customer) {

        return  customerDAO.insert(customer);

    }

    @Override
    public void delete(Customer customer) {
        customerDAO.delete(customer);
    }

    @Override
    public void update(Customer customer, Integer customerID) {
        customerDAO.update(customer,customerID);
    }

    @Override
    public boolean isPresent(Integer customerID) {
        return customerDAO.select(customerID).isPresent();
    }
    public boolean isPresent(String phoneNumber){
        return customerDAO.select(phoneNumber).isPresent();
    }

    public Integer countCustomersVehicle(Integer customerID){
        return  customerDAO.countCustomersVehicle(customerID);
    }
}
