package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;

public class UpdateCustomerCommand  implements Command<Customer> {
    private CustomerDAO customerDAO = new CustomerDAO();
    private Customer customer;

    public UpdateCustomerCommand(Customer customer) {
        this.customer = customer;
    }


    @Override
    public Customer execute() throws Exception {
        customerDAO.update(customer,customer.getCustomerID());
        return customer;
    }
}
