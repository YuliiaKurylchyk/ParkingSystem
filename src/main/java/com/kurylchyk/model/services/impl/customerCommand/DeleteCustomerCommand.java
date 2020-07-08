package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;

public class DeleteCustomerCommand implements Command<Void> {

    private CustomerDAO customerDAO = new CustomerDAO();
    private Customer customer;

    public DeleteCustomerCommand(Customer customer){
        this.customer = customer;
    }


    @Override
    public Void execute() throws ParkingSystemException {
        customerDAO.delete(customer);
        return null;
    }
}
