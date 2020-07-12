package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateCustomerCommand  implements Command<Void> {
    private CustomerDAO customerDAO;
    private Customer customer;
    private Logger logger = LogManager.getLogger(UpdateCustomerCommand.class);

    public UpdateCustomerCommand(Customer customer) {

        this.customer = customer;
        customerDAO = new CustomerDAO();
    }

    UpdateCustomerCommand(Customer customer,CustomerDAO customerDAO){
        this.customer = customer;
        this.customerDAO = customerDAO;
    }

    @Override
    public Void execute() throws ParkingSystemException {
        customerDAO.update(customer,customer.getCustomerID());
        logger.info("Customer "+customer.getCustomerID() + " was updated to "+ customer);
        return null;
    }
}
