package com.kurylchyk.controller;

import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;

import java.util.HashSet;
import java.util.LinkedHashMap;



public class Finder {



    public  LinkedHashMap<Integer, ParkingTicket> findByModelAndMake(HashSet<ParkingTicket> allTickets, String make, String model) throws NoSuchVehicleFoundException {

      LinkedHashMap<Integer,ParkingTicket> allFoundTickets = new LinkedHashMap<>();
        if (model.isEmpty()) {
            return findOnlyByMake(allTickets, make);
        }

        int index = 1;
        for (ParkingTicket parkingTicket : allTickets) {
            if (parkingTicket.getVehicle().getMake().equals(make)
                    && parkingTicket.getVehicle().getModel().equals(model)) {
                allFoundTickets.put(index++, parkingTicket);
            }
        }
        if(allFoundTickets.size()==0){
            throw new NoSuchVehicleFoundException("No such vehicle found.");
        }
        return allFoundTickets;

    }

    private LinkedHashMap<Integer, ParkingTicket> findOnlyByMake(HashSet<ParkingTicket> allTickets, String make) throws NoSuchVehicleFoundException {
        int index = 1;

        LinkedHashMap<Integer, ParkingTicket> allFoundTickets = new LinkedHashMap<>();
        for (ParkingTicket parkingTicket : allTickets) {
            if (parkingTicket.getVehicle().getMake().equals(make)) {
                allFoundTickets.put(index++, parkingTicket);
            }
        }
        if(allFoundTickets.size()==0){
            throw new NoSuchVehicleFoundException("No such vehicle found");
        }

        return allFoundTickets;
    }

    public LinkedHashMap<Integer,ParkingTicket> findBySurname(HashSet<ParkingTicket> allTickets,String surname) throws NoSuchParkingTicketException {

        int index = 1;

        LinkedHashMap<Integer, ParkingTicket> allFoundTickets = new LinkedHashMap<>();
        for(ParkingTicket currentParkingTicket: allTickets) {
            if(currentParkingTicket.getCustomer().getSurname().equals(surname)){
                allFoundTickets.put(index++, currentParkingTicket);
            }
        }
        if(allFoundTickets.size()==0){
            throw new NoSuchParkingTicketException("No such parking ticket found exception");
        }
        return allFoundTickets;
    }

    public ParkingTicket findByParkingID(HashSet<ParkingTicket> allTicket, String id) throws NoSuchParkingTicketException {

         for(ParkingTicket currentParkingTicket: allTicket) {
             if(currentParkingTicket.getParkingTickedID().equals(id)){
                 return currentParkingTicket;
             }
         }
         throw new NoSuchParkingTicketException("No such parking ticket found");
    }

}
