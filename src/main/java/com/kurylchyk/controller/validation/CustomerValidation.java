package com.kurylchyk.controller.validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CustomerValidation implements Validation {

    private String name;
    private String surname;
    private String phoneNumber;

    private CustomerValidation(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public static CustomerValidation fromRequestParameters(
            HttpServletRequest request) {
        return new CustomerValidation(
                request.getParameter("name"),
                request.getParameter("surname"),
                request.getParameter("phoneNumber"));
    }

    @Override
    public List<String> validate() {
        List violations = new ArrayList();


        String regex = "^\\D*$";
        if (name.equals("") || surname.equals("") || !name.matches(regex) || !surname.matches(regex)) {
            violations.add("Bad format of name or surname. Try again");
        }
        if (phoneNumber.equals("") || !(phoneNumber.charAt(0) == '0' && phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+"))) {
            violations.add("Bad format of phone number.It must start with 0. Try again");
        }
        return  violations;
    }


    @Override
    public void setAsRequestAttribute(HttpServletRequest req) {
        req.setAttribute("name", name);
        req.setAttribute("surname", surname);
        req.setAttribute("phoneNumber", phoneNumber);
    }
}
