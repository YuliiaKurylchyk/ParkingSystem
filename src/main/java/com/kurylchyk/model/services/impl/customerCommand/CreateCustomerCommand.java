package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.services.impl.Command;

public class CreateCustomerCommand  implements Command<Customer> {

    private Integer customerID;
    private String name;
    private String surname;
    private String phoneNumber;

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
    public Customer execute() throws Exception {
        Customer customer = Customer.newCustomer()
                .setCustomerID(customerID)
                .setName(name)
                .setSurname(surname)
                .setPhoneNumber(phoneNumber)
                .buildCustomer();

        return customer;
    }
}
