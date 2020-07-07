package com.kurylchyk.model.services;

import com.kurylchyk.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;

import com.kurylchyk.model.dao.ParkingSlotDTO;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingTicket.ParkingTicket;
import com.kurylchyk.model.vehicles.Vehicle;


//make it business exception
//make some of them void
public interface ParkingTicketService {

    List<ParkingTicket> getAllTickets() throws Exception;
    List<ParkingTicket> getAll(Status status) throws Exception;
    List<ParkingTicket> getAllInDate(LocalDateTime localDateTime) throws Exception;
    List<ParkingTicket> getAllInDateAndStatus(LocalDateTime localDateTime,Status status) throws Exception;
    ParkingTicket createParkingTicket(Vehicle vehicle, Customer customer, ParkingSlot parkingSlot) throws Exception;
    ParkingTicket getByID(Integer parkingTicketID) throws Exception;
    ParkingTicket getByVehicle(String licencePlate) throws Exception;
    List<ParkingTicket> getByCustomer(Integer customerID) throws Exception;
    ParkingTicket getByParkingSlot(ParkingSlotDTO identifier) throws Exception;
    ParkingTicket saveToDB(ParkingTicket parkingTicket) throws Exception;
    ParkingTicket update(ParkingTicket parkingTicket) throws Exception;
    ParkingTicket remove(ParkingTicket parkingTicket) throws Exception;
    ParkingTicket deleteCompletely(ParkingTicket parkingTicket) throws Exception;
    void updateParkingSlot(ParkingTicket parkingTicket,ParkingSlot newParkingSlot) throws Exception;
    boolean isPresent(Integer ParkingTicketID) throws Exception;
}
