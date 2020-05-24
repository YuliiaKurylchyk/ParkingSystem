package com.kurylchyk.model.customer;

public class CustomerBuilder {
    private Customer customer = new Customer();

    public CustomerBuilder setCustomerID(Integer id){
        customer.setCustomerID(id);
        return this;
    }
    public CustomerBuilder setName(String name) {
        customer.setName(name);
        return this;
    }

    public CustomerBuilder setSurname(String surname){
        customer.setSurname(surname);
        return this;
    }
    public CustomerBuilder setPhoneNumber(String phoneNumber){

        customer.setPhoneNumber(phoneNumber);
        return this;
    }

    public Customer buildCustomer(){
        return customer;
    }


}
