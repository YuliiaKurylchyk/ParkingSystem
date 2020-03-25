package com.kurylchyk;


public class Customer {
    private String name;
    private String surname;
    private String phoneNumber;
    //add driver's licence or sth like this

    @Override
    public String toString() {
        return name+" " +surname + " - "+ phoneNumber;
    }
    @Override
    public boolean equals(Object anotherCustomer){

        return ((Customer)anotherCustomer).name == this.name
                && ((Customer)anotherCustomer).surname ==this.surname
                && ((Customer)anotherCustomer).phoneNumber ==this.phoneNumber;
    }
}

