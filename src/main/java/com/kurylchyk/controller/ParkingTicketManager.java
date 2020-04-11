package com.kurylchyk.controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingPlace;
import com.kurylchyk.model.exceptions.NoAvailableSlot;
import com.kurylchyk.model.vehicles.Car;
import com.kurylchyk.model.vehicles.Motorbike;
import com.kurylchyk.model.vehicles.Truck;
import com.kurylchyk.model.vehicles.Vehicle;

public final class ParkingTicketManager {

    private static Scanner sc = new Scanner(System.in);
    private ArrayList<ParkingTicket> listOfTickets = new ArrayList<>();
    private ParkingLot parkingLot = new ParkingLot();

    private Customer getCustomerInfo() {
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
        System.out.println("Email: ");
        email = sc.nextLine();
        System.out.println("\n");

        return new Customer(name, surname, phoneNumber, email);

    }
    private Vehicle getVehicleInfo() {

        String type ;
        String sort;
        String model;
        String licencePlate;

        System.out.println("Choose the type of transport:");
        type = sc.nextLine();

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

        if(type.equalsIgnoreCase("motorbike")) {
           return  new Motorbike(sort, model, licencePlate);
        }
        if(type.equalsIgnoreCase("car")) {
            return new Car(sort, model, licencePlate);
        }
        if (type.equalsIgnoreCase("truck")) {
            return new Truck(sort,model,licencePlate,0);
        }
        else return null;

    }




    public void createParkingTicket(){
        Vehicle vehicle = getVehicleInfo();
        Customer customer = getCustomerInfo();
        ParkingPlace parkingPlace;
        try {
            parkingPlace = parkingLot.getParkingPlace(vehicle);
            listOfTickets.add(new ParkingTicket(vehicle, parkingPlace, customer));
        }catch (NoAvailableSlot exception) {
            System.out.println(exception);
        }

    }

    public void showAllAvailableTickets(){
        for(ParkingTicket ticket: listOfTickets) {
            System.out.println(ticket);
        }
    }

    public void deleteParkingTicket(String plate) {

        ParkingTicket currentTicket = null;

        for(int index = 0; index< listOfTickets.size(); index++) {
            if(listOfTickets.get(index).getVehicle().getRegistrationNumber().equals(plate)) {
                currentTicket = listOfTickets.get(index);
                listOfTickets.remove(index);
                break;
            }
        }
        currentTicket.getPayment();
        System.out.println("Removed successfully");
    }

    public static void main(String[] args) {

        ParkingTicketManager parkingTicketManager = new ParkingTicketManager();
        parkingTicketManager.createParkingTicket();
        parkingTicketManager.showAllAvailableTickets();
        parkingTicketManager.createParkingTicket();
        parkingTicketManager.showAllAvailableTickets();
        parkingTicketManager.createParkingTicket();
        parkingTicketManager.showAllAvailableTickets();
       try {
           Thread.sleep(5000);
       }catch (InterruptedException ex){
           ex.printStackTrace();
       }
       parkingTicketManager.deleteParkingTicket("BK 0330 IM");
       parkingTicketManager.showAllAvailableTickets();


    }



}
