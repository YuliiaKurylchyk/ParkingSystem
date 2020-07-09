package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllCustomersCommand implements Command<List<Customer>> {
    private CustomerDAO customerDAO = new CustomerDAO();
    private static final Logger logger = LogManager.getLogger(GetAllCustomersCommand.class);
    @Override
    public List<Customer> execute() throws ParkingSystemException {
        logger.debug("Getting all customers from database");
        return customerDAO.selectAll();
    }
}
