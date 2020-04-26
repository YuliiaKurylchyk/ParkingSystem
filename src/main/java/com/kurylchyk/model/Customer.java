package com.kurylchyk.model;


public class Customer {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email; //null


    public Customer(String name, String surname, String phoneNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

