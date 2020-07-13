package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creates customer using Builder
 */

public class CreateCustomerCommand  implements Command<Customer> {

    private Integer customerID;
    private String name;
    private String surname;
    private String phoneNumber;
    private  static final Logger logger = LogManager.getLogger(CreateCustomerCommand.class);

    public CreateCustomerCommand(String name,String surname,String phoneNumber){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public CreateCustomerCommand(Integer customerID,String name,String surname,String phoneNumber){
        this.customerID = customerID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Customer execute() throws ParkingSystemException {
        Customer customer = Customer.newCustomer()
                .setCustomerID(customerID)
                .setName(name)
                .setSurname(surname)
                .setPhoneNumber(phoneNumber)
                .buildCustomer();
        logger.debug("Customer was created");
        return customer;

    }
}
