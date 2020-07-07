package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.impl.customerCommand.*;
import com.kurylchyk.model.customer.Customer;

import java.util.List;


public class CustomerServiceImpl implements CustomerService {

    private CommandExecutor executor = new CommandExecutor();


    @Override
    public Customer create(String name, String surname, String phoneNumber) throws Exception {
        if (!executor.execute(new CheckCustomerIsPresentCommand(phoneNumber))) {
            return executor.execute(new CreateCustomerCommand(name, surname, phoneNumber));
        } else {
            return getFromDB(phoneNumber);
        }
    }
    @Override
    public Customer create(Integer customerID, String name, String surname, String phoneNumber)
            throws Exception {
        if (!executor.execute(new CheckCustomerIsPresentCommand(phoneNumber))) {
            return executor.execute(new CreateCustomerCommand(customerID, name, surname, phoneNumber));
        } else {
            return getFromDB(phoneNumber);
        }
    }


    @Override
    public Customer getFromDB(Integer customerID) throws Exception {
        return executor.execute(new GetCustomerCommand(customerID));
    }

    @Override
    public Customer getFromDB(String phoneNumber) throws Exception {
        System.out.println("In getFromDB with phoneNumber " + phoneNumber);
        return executor.execute(new GetCustomerCommand(phoneNumber));
    }

    @Override
    public Customer saveToDB(Customer customer) throws Exception {
        System.out.println(customer);
        if (!isPresent(customer.getPhoneNumber())) {
            return executor.execute(new SaveCustomerCommand(customer));
        } else {
            return getFromDB(customer.getPhoneNumber());
        }
    }

    @Override
    public void update(Customer customer) throws Exception {
        executor.execute(new UpdateCustomerCommand(customer));
    }

    @Override
    public List<Customer> getAll() throws Exception {
        return executor.execute(new GetAllCustomersCommand());
    }

    @Override
    public void deleteCompletely(Customer customer) throws Exception {

        executor.execute(new DeleteCustomerCommand(customer));
    }

    @Override
    public boolean isPresent(String phoneNumber) throws Exception {
        return executor.execute(new CustomerIsPresentCommand(phoneNumber));
    }


}
