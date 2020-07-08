package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;

public class SaveCustomerCommand  implements Command<Customer> {

    private CustomerDAO customerDAO = new CustomerDAO();
    private Customer customer;
    public SaveCustomerCommand(Customer customer) {
        this.customer = customer;
    }


    @Override
    public Customer execute() throws ParkingSystemException {
       Integer customerID  = customerDAO.insert(customer);
       customer.setCustomerID(customerID);
       return customer;
    }
}
