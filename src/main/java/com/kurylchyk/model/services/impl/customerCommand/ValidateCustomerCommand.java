package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.services.impl.Command;

import java.util.ArrayList;
import java.util.List;

public class ValidateCustomerCommand implements Command<List<String>> {
    private String name;
    private String surname;
    private String phoneNumber;

    public ValidateCustomerCommand(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public List<String> execute() throws Exception {
        List violations = new ArrayList();

        String regex = "^\\D*$";
        if (name.isEmpty()|| surname.isEmpty() || !name.matches(regex) || !surname.matches(regex)) {
            violations.add("Bad format of name or surname. Try again");
        }
        if (phoneNumber.isEmpty()|| !(phoneNumber.charAt(0) == '0' && phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+"))) {
            violations.add("Bad format of phone number.It must start with 0. Try again");
        }
        return  violations;
    }
}
