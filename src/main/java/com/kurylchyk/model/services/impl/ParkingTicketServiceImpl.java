package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotDTO;
import com.kurylchyk.model.services.impl.ticketCommand.*;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;

import java.time.LocalDateTime;
import java.util.List;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.vehicles.Vehicle;

public class ParkingTicketServiceImpl implements ParkingTicketService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public List<ParkingTicket> getAllTickets() throws ParkingSystemException {
        return executor.execute(new GetAllTicketsCommand());
    }

    @Override
    public List<ParkingTicket> getAll(Status status) throws ParkingSystemException {
        return executor.execute(new GetAllTicketsCommand(status));
    }

    @Override
    public List<ParkingTicket> getAllInDate(LocalDateTime localDateTime) throws ParkingSystemException {
        return executor.execute(new GetInDateCommand(localDateTime));
    }

    @Override
    public List<ParkingTicket> getAllInDateAndStatus(LocalDateTime localDateTime, Status status) throws ParkingSystemException {
        return executor.execute(new GetInDateCommand(localDateTime,status));
    }

    @Override
    public ParkingTicket createParkingTicket(Vehicle vehicle, Customer customer, ParkingSlot parkingSlot) throws ParkingSystemException{
       return executor.execute(new CreateParkingTicketCommand(vehicle,customer,parkingSlot));
    }


    @Override
    public ParkingTicket getByID(Integer parkingTicketID) throws ParkingSystemException {
        return executor.execute(new GetParkingTicketCommand(parkingTicketID));
    }

    @Override
    public ParkingTicket getByVehicle(String licencePlate) throws ParkingSystemException{
        return executor.execute(new GetByVehicleCommand(licencePlate));
    }

    @Override
    public List<ParkingTicket> getByCustomer(Integer customerID) throws ParkingSystemException{
        return executor.execute(new GetByCustomerCommand(customerID));
    }

    @Override
    public ParkingTicket getByParkingSlot(ParkingSlotDTO identifier) throws ParkingSystemException {
        return  executor.execute(new GetByParkingSlotCommand(identifier));
    }


    @Override
    public ParkingTicket saveToDB(ParkingTicket parkingTicket) throws ParkingSystemException {
        return executor.execute(new SaveParkingTicketCommand(parkingTicket));
    }

    @Override
    public ParkingTicket update(ParkingTicket parkingTicket) throws ParkingSystemException {
        return executor.execute(new UpdateTicketCommand(parkingTicket));

    }


    @Override
    public ParkingTicket remove(ParkingTicket parkingTicket) throws ParkingSystemException {
        return executor.execute(new RemoveParkingTicketCommand(parkingTicket));
    }

    @Override
    public ParkingTicket deleteCompletely(ParkingTicket parkingTicket) throws ParkingSystemException {
        executor.execute(new DeleteTicketCompletely(parkingTicket));
        return parkingTicket;
    }

    @Override
    public void updateParkingSlot(ParkingTicket parkingTicket, ParkingSlot newParkingSlot) throws ParkingSystemException {
        executor.execute(new UpdateSlotInTicketCommand(parkingTicket,newParkingSlot));
    }

}
