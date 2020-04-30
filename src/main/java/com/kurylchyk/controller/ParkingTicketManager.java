package com.kurylchyk.controller;


import java.util.Scanner;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingLot;
import com.kurylchyk.model.ParkingTicket;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.exceptions.NoAvailableParkingPlaceException;
import com.kurylchyk.model.exceptions.NoSuchTypeOfVehicleException;
import com.kurylchyk.model.vehicles.*;

public final class ParkingTicketManager {

    private static Scanner sc = new Scanner(System.in);
    private ParkingLot parkingLot = new ParkingLot();
    private static int countOfParkingTickets = 0;
    private Remover remover  = new Remover();

    private Customer setCustomerInfo() {
        String name = "";
        String surname = "";
        String phoneNumber = "";
        String email = "";
        System.out.println("Please, enter customer info : ");
        System.out.print("Name: ");
        name = sc.nextLine();
        System.out.println("\n");
        System.out.print("Surname :");
        surname = sc.nextLine();
        System.out.println("\n");
        System.out.print("Phone number :");
        phoneNumber = sc.nextLine();


        return new Customer(name, surname, phoneNumber);

    }

    private Vehicle setVehicleInfo() throws NoSuchTypeOfVehicleException {

        TypeOfVehicle type;
        String sort;
        String model;
        String licencePlate;

        System.out.println("Choose the type of transport:");
        try {
            type = TypeOfVehicle.valueOf(sc.nextLine().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new NoSuchTypeOfVehicleException("No such type of vehicle", ex);
        }

        System.out.println("Please, enter customer info : ");
        System.out.print("Sort: ");
        sort = sc.nextLine();
        System.out.println("\n");
        System.out.print("Model: ");
        model = sc.nextLine();
        System.out.println("\n");
        System.out.print("Licence Plate: ");
        licencePlate = sc.nextLine();
        System.out.println("\n");

        switch (type) {
            case MOTORBIKE:
                return new Motorbike(sort, model, licencePlate);
            case CAR:
                return new Car(sort, model, licencePlate);
            case TRUCK:
                return new Truck(sort, model, licencePlate);
            case BUS:
                return  new Bus(sort,model,licencePlate);
        }
        return null;
    }

    public void createParkingTicket() throws NoSuchTypeOfVehicleException {
        Vehicle vehicle = setVehicleInfo();

        Customer customer = setCustomerInfo();
        ParkingSlot parkingSlot;
        try {
            parkingSlot = parkingLot.getParkingPlace(vehicle);
            ParkingTicketDB.addNewActiveTicket(new ParkingTicket(vehicle, parkingSlot, customer));
        } catch (NoAvailableParkingPlaceException exception) {
            System.out.println(exception);
        }

    }

    public void showActiveTicket() {

        ParkingTicketDB.showActiveTickets();
    }

    public void showTicketsInDB() {

        ParkingTicketDB.showUsedTickets();
    }

    public void removeParkingTicket() {

        System.out.println("Choose option how to remove: ");
        System.out.println("1 - by registration number");
        System.out.println("2 - by model and/or make of vehicle");
        System.out.println("3 - surname");
        System.out.println("4 - remove by parking ticket ID");
        System.out.println("5  - back to menu");
        Scanner sc = new Scanner(System.in);
        ParkingTicket ticketToBeRemoved = null;


        int answer = sc.nextInt();
        try {
            switch (answer) {
                case 1:
                    ticketToBeRemoved =remover.removeByRegistrationNumber();
                    break;
                case 2:
                    ticketToBeRemoved = remover.removeByMakeAndModel();
                    break;
                case 3:
                    ticketToBeRemoved = remover.removeBySurname();
                    break;
                case 4:
                    ticketToBeRemoved = remover.removeByParkingTicketID();
                    break;
                case 5:
                    return;

            }
        } catch (NoSuchVehicleFoundException | NoSuchParkingTicketException exception) {
            System.out.println(exception);
            return;
        }

        ParkingTicketDB.removeActiveTicket(ticketToBeRemoved);
    }


}
