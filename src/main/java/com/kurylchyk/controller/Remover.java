package com.kurylchyk.controller;

import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class Remover{


    private Finder finder = new Finder();

    public ParkingTicket removeByRegistrationNumber() throws NoSuchVehicleFoundException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter registration number: ");
        String registrationNumber = sc.nextLine();

        for (ParkingTicket parkingTicket : ParkingTicketDB.getListOfActiveTickets()) {
            if (parkingTicket.getVehicle().getLicencePlate().equals(registrationNumber)) {
                return parkingTicket;
            }
        }
        throw new NoSuchVehicleFoundException("No such vehicle found");
    }

    public ParkingTicket removeBySurname() throws NoSuchParkingTicketException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter surname of customer: ");
        String surname = sc.nextLine();

        LinkedHashMap<Integer, ParkingTicket> allFoundTickets
                = finder.findBySurname(ParkingTicketDB.getListOfActiveTickets(), surname);

        ParkingTicketDB.show(allFoundTickets);

        System.out.print("Choose option to remove: ");
        int option = sc.nextInt();

        ParkingTicket parkingTicket = allFoundTickets.remove(option);
        return parkingTicket;
    }

    public ParkingTicket removeByParkingTicketID() throws NoSuchParkingTicketException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ticket ID: ");
        String id =  sc.nextLine();
        return finder.findByParkingID(ParkingTicketDB.getListOfActiveTickets(), id);
    }


    public ParkingTicket removeByMakeAndModel() throws NoSuchVehicleFoundException {

        System.out.print("Enter the make: ");
        Scanner sc = new Scanner(System.in);
        String vehicleMake = sc.nextLine();
        System.out.println();
        System.out.print("Enter model: ");
        String vehicleModel = sc.nextLine();

        LinkedHashMap<Integer, ParkingTicket> allFoundTickets = finder.findByModelAndMake(ParkingTicketDB.getListOfActiveTickets(), vehicleMake, vehicleModel);
        if (allFoundTickets.size() == 0) {
            throw new NoSuchVehicleFoundException("No such vehicle found");
        }

        ParkingTicketDB.show(allFoundTickets);


        System.out.print("Choose option to remove: ");
        int option = sc.nextInt();

        ParkingTicket parkingTicket = allFoundTickets.remove(option);
        return parkingTicket;
    }

}
