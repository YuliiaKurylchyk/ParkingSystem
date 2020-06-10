package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.vehicles.Vehicle;

public class GetByVehicleCommand implements Command<ParkingTicket> {
    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();
    private String licencePlate;

    public GetByVehicleCommand(String licencePlate){
        this.licencePlate = licencePlate;
    }


    //or else throw
    @Override
    public ParkingTicket execute() throws Exception {
      return   parkingTicketDAO.selectByVehicleID(licencePlate).get();
    }
}
