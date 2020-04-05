package com.kurylchyk.controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.kurylchyk.model.Customer;
import com.kurylchyk.model.ParkingPlace;
import com.kurylchyk.model.Vehicle;

public final class ParkingTicketManager {

    private static Scanner sc = new Scanner(System.in);
    private ArrayList<ParkingTicket> listOfTickets = new ArrayList<>();

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
        String sort;
        String model;
        String licencePlate;

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

        return new Vehicle(sort, model, licencePlate);
    }

    private ParkingPlace getParkingPlaceInfo() {

        Random random = new Random(47);

        int currentParkingPlace = 0;
       generateAgain: do {
           currentParkingPlace  = random.nextInt(100);
           if(listOfTickets.size()==0){
               break;
           }

            for (ParkingTicket parkingTicket : listOfTickets) {
                if (parkingTicket.getParkingPlace() == currentParkingPlace) {
                        continue generateAgain;
                }else {
                    break generateAgain;
                }
            }
        }while(true);

        return new ParkingPlace(currentParkingPlace);

    }

    public void createParkingTicket(){

        listOfTickets.add(new ParkingTicket(getVehicleInfo(),getParkingPlaceInfo(),getCustomerInfo()));



    }

    public void showAllAvailableTickets(){
        for(ParkingTicket ticket: listOfTickets) {
            System.out.println(ticket);
        }
    }

    public void deleteParkingTicket(String plate) {

        ParkingTicket currentTicket = null;

        for(int index = 0; index< listOfTickets.size(); index++) {
            if(listOfTickets.get(index).getVehicle().getLicencePlate().equals(plate)) {
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
           Thread.sleep(500000);
       }catch (InterruptedException ex){
           ex.printStackTrace();
       }
       parkingTicketManager.deleteParkingTicket("BK 0330 IM");
       parkingTicketManager.showAllAvailableTickets();


    }



}
