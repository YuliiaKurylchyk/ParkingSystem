package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Checks if customer is present in db
 * or not by phone number
 *
 */


public class CheckCustomerIsPresentCommand implements Command<Boolean> {

    private String phoneNumber;
    private CustomerDAO customerDAO = new CustomerDAO();
    private static final Logger logger = LogManager.getLogger(CheckCustomerIsPresentCommand.class);
    public CheckCustomerIsPresentCommand(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    CheckCustomerIsPresentCommand(String phoneNumber,CustomerDAO customerDAO)
    {
        this.phoneNumber = phoneNumber;
        this.customerDAO = customerDAO;
    }
    @Override
    public Boolean execute() throws ParkingSystemException {

        logger.debug("Checking is customer is present in database by "+ phoneNumber);
        return customerDAO.isPresent(phoneNumber);
    }
}
