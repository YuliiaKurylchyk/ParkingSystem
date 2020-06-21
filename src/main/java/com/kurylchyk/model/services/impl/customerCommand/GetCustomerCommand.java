package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;

public class GetCustomerCommand implements Command<Customer> {
    private CustomerDAO customerDAO = new CustomerDAO();
    private Integer customerID;
    private String phoneNumber;
    public GetCustomerCommand(Integer customerID) {
        this.customerID = customerID;
    }

    public GetCustomerCommand(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Customer execute() throws Exception {
        Customer customer = null;
        if(phoneNumber!=null){
            System.out.println("in GetCustomerCommand with pn "+phoneNumber);
            customer = customerDAO.select(phoneNumber).orElseThrow(() ->
                    new NoSuchCustomerFoundException("No customer with phone number " +phoneNumber + " found"));;
        }else if(customerID!=null){
            customer = customerDAO.select(customerID).orElseThrow(() -> new NoSuchCustomerFoundException("No customer found"));
        }
        return customer;
    }
}
