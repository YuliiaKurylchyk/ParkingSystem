package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.impl.CommandExecutor;
import com.kurylchyk.model.services.impl.vehicleCommand.CheckVehicleInDatabase;
import com.kurylchyk.model.vehicles.Vehicle;

public class UpdateVehicleInTicketCommand implements Command<ParkingTicket> {

    private Vehicle vehicle;
    private String currentLicensePlate;
    private CommandExecutor executor = new CommandExecutor();


    public UpdateVehicleInTicketCommand(Vehicle vehicle, String currentLicensePlate) {
        this.vehicle = vehicle;
        this.currentLicensePlate = currentLicensePlate;
    }


    @Override
    public ParkingTicket execute() throws Exception {
        ParkingTicket parkingTicket  = executor.execute(new GetByVehicleCommand(currentLicensePlate));
        parkingTicket.setVehicle(vehicle);
        executor.execute(new UpdateTicket(parkingTicket));
        return parkingTicket;
    }
}
