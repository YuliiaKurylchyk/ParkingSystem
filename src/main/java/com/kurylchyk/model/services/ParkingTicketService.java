package com.kurylchyk.model.services;

import com.kurylchyk.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;

import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.vehicles.Vehicle;


//make it business exception
//make some of them void
public interface ParkingTicketService {

    List<ParkingTicket> getAllTickets() throws ParkingSystemException;
    List<ParkingTicket> getAll(Status status) throws ParkingSystemException;
    List<ParkingTicket> getAllInDate(LocalDateTime localDateTime) throws ParkingSystemException;
    List<ParkingTicket> getAllInDateAndStatus(LocalDateTime localDateTime,Status status) throws ParkingSystemException;
    ParkingTicket createParkingTicket(Vehicle vehicle, Customer customer, ParkingSlot parkingSlot) throws ParkingSystemException;
    ParkingTicket getByID(Integer parkingTicketID) throws ParkingSystemException;
    ParkingTicket getByVehicle(String licencePlate) throws ParkingSystemException;
    List<ParkingTicket> getByCustomer(Integer customerID) throws ParkingSystemException;
    ParkingTicket getByParkingSlot(ParkingSlotDTO identifier) throws ParkingSystemException;
    ParkingTicket saveToDB(ParkingTicket parkingTicket) throws ParkingSystemException;
    ParkingTicket update(ParkingTicket parkingTicket) throws ParkingSystemException;
    ParkingTicket remove(ParkingTicket parkingTicket) throws ParkingSystemException;
    ParkingTicket deleteCompletely(ParkingTicket parkingTicket) throws ParkingSystemException;
    void updateParkingSlot(ParkingTicket parkingTicket,ParkingSlot newParkingSlot) throws ParkingSystemException;
    boolean isPresent(Integer ParkingTicketID) throws ParkingSystemException;
}
