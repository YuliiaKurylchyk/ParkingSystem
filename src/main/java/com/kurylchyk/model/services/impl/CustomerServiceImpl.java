package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.CustomerService;
import com.kurylchyk.model.services.impl.customerCommand.*;
import com.kurylchyk.model.domain.customer.Customer;

import java.util.List;


//подивтись чи використовуэться isPresent()
//в gETcUSTOMER COMMAND  э пошук по айды, подивитись чи вын десь використовуэться
//робити customer dto v update

public class CustomerServiceImpl implements CustomerService {

    private CommandExecutor executor = new CommandExecutor();


    @Override
    public Customer create(String name, String surname, String phoneNumber) throws ParkingSystemException {
        if (!executor.execute(new CheckCustomerIsPresentCommand(phoneNumber))) {
            return executor.execute(new CreateCustomerCommand(name, surname, phoneNumber));
        } else {
            return getFromDB(phoneNumber);
        }
    }
    @Override
    public Customer create(Integer customerID, String name, String surname, String phoneNumber)
            throws ParkingSystemException {
        if (!executor.execute(new CheckCustomerIsPresentCommand(phoneNumber))) {
            return executor.execute(new CreateCustomerCommand(customerID, name, surname, phoneNumber));
        } else {
            return getFromDB(phoneNumber);
        }
    }


    @Override
    public Customer getFromDB(Integer customerID) throws ParkingSystemException {
        return executor.execute(new GetCustomerCommand(customerID));
    }

    @Override
    public Customer getFromDB(String phoneNumber) throws ParkingSystemException {
        return executor.execute(new GetCustomerCommand(phoneNumber));
    }

    @Override
    public Customer saveToDB(Customer customer) throws ParkingSystemException {
        if (!isPresent(customer.getPhoneNumber())) {
            return executor.execute(new SaveCustomerCommand(customer));
        } else {
            return getFromDB(customer.getPhoneNumber());
        }
    }

    @Override
    public void update(Customer customer) throws ParkingSystemException {
        executor.execute(new UpdateCustomerCommand(customer));
    }

    @Override
    public List<Customer> getAll() throws ParkingSystemException {
        return executor.execute(new GetAllCustomersCommand());
    }

    @Override
    public void deleteCompletely(Customer customer) throws ParkingSystemException {

        executor.execute(new DeleteCustomerCommand(customer));
    }

    //check if it is used????
    @Override
    public boolean isPresent(String phoneNumber) throws ParkingSystemException {
        return executor.execute(new CheckCustomerIsPresentCommand(phoneNumber));
    }


}
