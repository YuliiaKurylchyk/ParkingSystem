package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.dao.ParkingSlotIdentifier;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.ticketCommand.*;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.parkingTicket.Status;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.vehicles.Vehicle;

public class ParkingTicketServiceImpl implements ParkingTicketService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public List<ParkingTicket> getAllTickets() throws Exception {
        return executor.execute(new GetAllTicketCommand());
    }

    @Override
    public List<ParkingTicket> getAll(Status status) throws Exception {
        return executor.execute(new GetAllTicketCommand(status));
    }

    @Override
    public List<ParkingTicket> getAllInDate(LocalDateTime localDateTime) throws Exception {
        return executor.execute(new GetInDateCommand(localDateTime));
    }

    @Override
    public List<ParkingTicket> getAllInDateAndStatus(LocalDateTime localDateTime, Status status) throws Exception {
        return executor.execute(new GetInDateCommand(localDateTime,status));
    }

    @Override
    public ParkingTicket createParkingTicket(Vehicle vehicle, Customer customer, ParkingSlot parkingSlot) throws Exception {
       return executor.execute(new CreateParkingTicketCommand(vehicle,customer,parkingSlot));
    }


    @Override
    public ParkingTicket getByID(Integer parkingTicketID) throws Exception {
        return executor.execute(new GetParkingTicketCommand(parkingTicketID));
    }

    @Override
    public ParkingTicket getByVehicle(String licencePlate) throws Exception {
        return executor.execute(new GetByVehicleCommand(licencePlate));
    }

    @Override
    public List<ParkingTicket> getByCustomer(Integer customerID) throws Exception {
        return executor.execute(new GetByCustomerCommand(customerID));
    }

    @Override
    public ParkingTicket getByParkingSlot(ParkingSlotIdentifier identifier) throws Exception {
        return  executor.execute(new GetByParkingSlotCommand(identifier));
    }


    @Override
    public ParkingTicket saveToDB(ParkingTicket parkingTicket) throws Exception {
        return executor.execute(new SaveParkingTicketCommand(parkingTicket));
    }

    @Override
    public ParkingTicket update(ParkingTicket parkingTicket) throws Exception {
        return executor.execute(new UpdateTicket(parkingTicket));

    }

    public ParkingTicket updateVehicleInfo(Vehicle vehicle, String currentLicensePlate)
            throws Exception {
         return  executor.execute(new UpdateVehicleInTicketCommand(vehicle,currentLicensePlate));
    }

    @Override
    public ParkingTicket remove(ParkingTicket parkingTicket) throws Exception {
        return executor.execute(new RemoveParkingTicketCommand(parkingTicket));
    }

    @Override
    public ParkingTicket deleteCompletely(ParkingTicket parkingTicket) throws Exception {
        executor.execute(new DeleteTicketCompletely(parkingTicket));
        return parkingTicket;
    }


    @Override
    public boolean isPresent(Integer parkingTicketID) throws Exception {
       return executor.execute(new TicketIsPresentCommand(parkingTicketID));
    }
}
