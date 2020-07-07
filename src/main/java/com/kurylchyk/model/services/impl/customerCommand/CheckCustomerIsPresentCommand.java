package com.kurylchyk.model.services.impl.customerCommand;

import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.services.impl.Command;

public class CheckCustomerIsPresentCommand implements Command<Boolean> {

    private String phoneNumber;
    private CustomerDAO customerDAO = new CustomerDAO();
    public CheckCustomerIsPresentCommand(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Boolean execute() throws Exception {

        if (customerDAO.isPresent(phoneNumber)) {
            return true;
        } else {
            return false;
        }
    }
}
