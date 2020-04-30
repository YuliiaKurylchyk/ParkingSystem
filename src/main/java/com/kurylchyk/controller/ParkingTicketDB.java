package com.kurylchyk.controller;

import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.parkingSlots.ParkingSlot;

import java.util.*;

public class ParkingTicketDB {

    private static HashSet<ParkingTicket> listOfActiveTickets = new LinkedHashSet<>();
    private static HashSet<ParkingTicket> listOfUsedTickets = new LinkedHashSet<>();


    public static void show(Collection<? extends ParkingTicket> parkingTickets){

        for(ParkingTicket current:parkingTickets) {
            System.out.println(current);
        }
    }

    public static void show(Map<Integer, ParkingTicket> parkingTickets) {

        for(Map.Entry<Integer, ParkingTicket> currentParkingTicket: parkingTickets.entrySet() ) {
            System.out.println("№" + currentParkingTicket.getKey() +"\n" + currentParkingTicket.getValue());
        }
    }

    public static  void showActiveTickets() {

        if(listOfActiveTickets.size()==0) {
            System.out.println("No active tickets available");
            return;
        }
        System.out.println("Active tickets");
        for(ParkingTicket parkingTicket:listOfActiveTickets) {
            System.out.println(parkingTicket);
        }
    }

    public static void showUsedTickets(){
        if(listOfUsedTickets.size()==0) {
            System.out.println("No past tickets available");
            return;
        }

        for(ParkingTicket parkingTicket: listOfUsedTickets) {
            System.out.println(parkingTicket);
        }
    }

    public  static void addNewActiveTicket(ParkingTicket parkingTicket) {

        listOfActiveTickets.add(parkingTicket);
    }

    public  static ParkingSlot removeActiveTicket(ParkingTicket parkingTicketToRemove) {


        for (ParkingTicket parkingTicket: listOfActiveTickets){

            if(parkingTicket.equals(parkingTicketToRemove)){
                listOfActiveTickets.remove(parkingTicket);
                parkingTicket.getPayment();
                parkingTicket.setStatus("left");
                listOfUsedTickets.add(parkingTicket);
                return parkingTicket.getParkingSlot();
            }
        }
        throw new NoSuchElementException();
    }

    public static HashSet<ParkingTicket> getListOfActiveTickets() {
        return listOfActiveTickets;
    }

}
