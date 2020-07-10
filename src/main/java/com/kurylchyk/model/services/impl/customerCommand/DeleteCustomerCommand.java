package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteCustomerCommand implements Command<Void> {

    private CustomerDAO customerDAO = new CustomerDAO();
    private Customer customer;
    private static final Logger logger = LogManager.getLogger(DeleteCustomerCommand.class);

    public DeleteCustomerCommand(Customer customer){
        this.customer = customer;
    }


    @Override
    public Void execute() throws ParkingSystemException {
        customerDAO.delete(customer);
        logger.info("Customer "+ customer + " was deleted from database");
        return null;
    }
}
