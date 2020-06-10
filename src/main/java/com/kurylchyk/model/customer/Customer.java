package com.kurylchyk.model.customer;


public class Customer {
    private Integer customerID;
    private String name;
    private String surname;
    private String phoneNumber;

    public Customer(){}


    public static CustomerBuilder newCustomer(){
        return new CustomerBuilder();
    }

   // public Customer(String name, String surname, String phoneNumber) {
     //   this.name = name;
       // this.surname = surname;
        //this.phoneNumber = phoneNumber;
    //}
    /*

    public Customer(Integer customerID,String name, String surname, String phoneNumber) {
        this.customerID = customerID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }


     */
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSurname() {
        return surname;
    }
    @Override
    public String toString() {
        return name + " " + surname + " - " + phoneNumber;
    }

    @Override
    public boolean equals(Object anotherCustomer) {
        if (this == anotherCustomer) return true;
        if (anotherCustomer == null || getClass() != anotherCustomer.getClass()) return false;
        Customer customer = (Customer) anotherCustomer;

        return customer.name == this.name
                && customer.surname == this.surname
                && customer.phoneNumber == this.phoneNumber;
    }
}

