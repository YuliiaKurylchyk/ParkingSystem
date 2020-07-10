package com.kurylchyk.model.services.impl.customerCommand;


import com.kurylchyk.model.exceptions.NoSuchCustomerFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetCustomerCommand implements Command<Customer> {
    private CustomerDAO customerDAO = new CustomerDAO();
    private Integer customerID;
    private String phoneNumber;
    private static final Logger logger = LogManager.getLogger(GetCustomerCommand.class);


    public GetCustomerCommand(Integer customerID) {
        this.customerID = customerID;
    }

    public GetCustomerCommand(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Customer execute() throws ParkingSystemException {
        Customer customer = null;
        if(phoneNumber!=null){
            customer = customerDAO.select(phoneNumber).orElseThrow(() ->
                    new NoSuchCustomerFoundException("No customer with phone number " +phoneNumber + " found"));;
        }

        else if(customerID!=null){
            customer = customerDAO.select(customerID).orElseThrow(() ->
                    new NoSuchCustomerFoundException("No customer found"));
        }

        logger.debug("Customer "+ customer.getCustomerID() +" was retrieved from database");
        return customer;
    }
}
