package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.impl.CommandExecutor;
import com.kurylchyk.model.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateVehicleInTicketCommand implements Command<ParkingTicket> {

    private Vehicle vehicle;
    private String currentLicensePlate;
    private CommandExecutor executor = new CommandExecutor();
    private Logger logger = LogManager.getLogger(UpdateVehicleInTicketCommand.class);


    public UpdateVehicleInTicketCommand(Vehicle vehicle, String currentLicensePlate) {
        this.vehicle = vehicle;
        this.currentLicensePlate = currentLicensePlate;
    }


    @Override
    public ParkingTicket execute() throws ParkingSystemException {

        ParkingTicket parkingTicket  = executor.execute(new GetByVehicleCommand(currentLicensePlate));
        parkingTicket.setVehicle(vehicle);
        executor.execute(new UpdateTicketCommand(parkingTicket));
        logger.info("Parking ticket "+parkingTicket.getParkingTicketID() + " was updated with license plate values "+ vehicle.getLicensePlate());
        return parkingTicket;
    }
}
