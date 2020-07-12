package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveCustomerCommand implements Command<Customer> {

    private CustomerDAO customerDAO;
    private Customer customer;
    private static final Logger logger = LogManager.getLogger(SaveCustomerCommand.class);

    public SaveCustomerCommand(Customer customer) {
        customerDAO = new CustomerDAO();
        this.customer = customer;
    }

    SaveCustomerCommand(Customer customer, CustomerDAO customerDAO) {
        this.customer = customer;
        this.customerDAO = customerDAO;
    }

    @Override
    public Customer execute() throws ParkingSystemException {
        Integer customerID = customerDAO.insert(customer);
        customer.setCustomerID(customerID);
        logger.info("customer " + customer + "was successfully saved to database");
        return customer;
    }
}
