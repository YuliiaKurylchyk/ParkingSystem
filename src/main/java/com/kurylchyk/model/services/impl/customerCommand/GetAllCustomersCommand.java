package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.services.impl.Command;
import java.util.List;

public class GetAllCustomersCommand implements Command<List<Customer>> {
    private CustomerDAO customerDAO = new CustomerDAO();
    @Override
    public List<Customer> execute() throws Exception {
        return customerDAO.selectAll();
    }
}
