package com.kurylchyk.model;


public class Customer {
    private String name;
    private String surname;
    private String phoneNumber;
    private  String email; //null
    //add driver's licence or sth like this


   public  Customer(String name,String surname,String phoneNumber, String email) {
       this.name = name;
       this.surname = surname;
       this.phoneNumber = phoneNumber;
       this.email = email;
   }

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

