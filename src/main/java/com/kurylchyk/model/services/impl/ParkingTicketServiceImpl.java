package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.ticketCommand.*;
import com.kurylchyk.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.vehicles.Vehicle;
import com.kurylchyk.model.parkingTicket.Status;

public class ParkingTicketServiceImpl implements ParkingTicketService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public List<ParkingTicket> getAllTickets() throws Exception {
        return executor.execute(new GetAllTicketCommand());
    }

    @Override
    public List<ParkingTicket> getAll(String status) throws Exception {
        return executor.execute(new GetAllTicketCommand(status));
    }

    @Override
    public List<ParkingTicket> getAllInDate(LocalDateTime localDateTime) throws Exception {
        return executor.execute(new GetInDateCommand(localDateTime));
    }

    @Override
    public List<ParkingTicket> getAllInDateAndStatus(LocalDateTime localDateTime, String status) throws Exception {
        return executor.execute(new GetInDateCommand(localDateTime,status));
    }

    @Override
    public ParkingTicket createParkingTicket(Vehicle vehicle, Customer customer, ParkingSlot parkingSlot) {
        ParkingTicket parkingTicket = ParkingTicket.newParkingTicket()
                .withVehicle(vehicle)
                .withCustomer(customer)
                .withParkingSlot(parkingSlot)
                .withArrivalTime(LocalDateTime.now())
                .withStatus(Status.PRESENT).buildTicket();
        return parkingTicket;
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
    public ParkingTicket saveToDB(ParkingTicket parkingTicket) throws Exception {
        return executor.execute(new SaveToBD(parkingTicket));
    }

    @Override
    public ParkingTicket update(ParkingTicket parkingTicket) throws Exception {
        return executor.execute(new UpdateTicket(parkingTicket));

    }

    @Override
    public ParkingTicket remove(ParkingTicket parkingTicket) throws Exception {
        return executor.execute(new RemoveTicket(parkingTicket));
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
