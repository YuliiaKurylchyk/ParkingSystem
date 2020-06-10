package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.CustomerDAO;

public class CustomerIsPresentCommand implements Command<Boolean> {
    private CustomerDAO customerDAO = new CustomerDAO();
    private String phoneNumber;

    public CustomerIsPresentCommand(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Boolean execute() throws Exception {
        return customerDAO.isPresent(phoneNumber);
    }
}
