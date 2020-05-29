package com.kurylchyk.model.service;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.parkingTicket.ParkingTicket;

import java.time.LocalDateTime;
import java.util.List;

public class ParkingTicketService implements ServiceAddDelete<ParkingTicket, Integer>,ServiceGetUpdate<ParkingTicket,Integer> {

    private ParkingTicketDAO parkingTicketDAO = new ParkingTicketDAO();

    @Override
    public ParkingTicket get(Integer parkingTicketID) throws NoSuchParkingTicketException{
        return parkingTicketDAO.select(parkingTicketID)
                .orElseThrow(() -> new NoSuchParkingTicketException("No such parking ticket with id " + parkingTicketID + " found"));
    }

    //обробити шо тіпа list empty
    @Override
    public List<ParkingTicket> getAll() {
        return parkingTicketDAO.selectAll();
    }

    public List<ParkingTicket> getAll(String currentStatus) {
        return parkingTicketDAO.selectAll(currentStatus);
    }

    public List<ParkingTicket> getInDate(LocalDateTime date){
        return parkingTicketDAO.selectInDate(date);
    }
    public List<ParkingTicket> getInDateAndStatus(LocalDateTime date,String currentStatus) {
        return parkingTicketDAO.selectInDateAndStatus(date,currentStatus);
    }


    public ParkingTicket getByVehicle(String vehicleID) throws Exception {
        return parkingTicketDAO.selectByVehicleID(vehicleID)
                .orElseThrow(() -> new NoSuchParkingTicketException("Parking ticket with licence plate " + vehicleID + "is found"));
    }

    public List<ParkingTicket> getByCustomer(Integer customerID) throws Exception {

        List<ParkingTicket> allFoundTickets = parkingTicketDAO.selectByCustomerID(customerID);
        if (allFoundTickets.isEmpty()) {
            throw new NoSuchParkingTicketException("No ticket with customer id " + customerID + " found");
        }
        return allFoundTickets;
    }

    @Override
    public Integer add(ParkingTicket parkingTicket) {
        return parkingTicketDAO.insert(parkingTicket);

    }

    @Override
    public void delete(ParkingTicket parkingTicket) {
        parkingTicketDAO.delete(parkingTicket);
    }

    @Override
    public void update(ParkingTicket parkingTicket, Integer parkingTicketID) {
        parkingTicketDAO.update(parkingTicket, parkingTicketID);
    }

    @Override
    public boolean isPresent(Integer parkingTicketID) {
        return parkingTicketDAO.select(parkingTicketID).isPresent();
    }
}
