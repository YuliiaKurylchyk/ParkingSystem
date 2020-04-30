package com.kurylchyk.controller;

import com.kurylchyk.model.exceptions.NoSuchTypeOfVehicleException;
import com.kurylchyk.view.View;

import java.util.Scanner;

public class Controller {
    private View view = new View();
    private ParkingTicketManager parkingTicketManager = new ParkingTicketManager();

    public void chooseOption() {
        Scanner scanner = new Scanner(System.in);
        view.showMenu();
        int answer = scanner.nextInt();
        switch (answer) {
            case 1:
                parkingTicketManager.showActiveTicket();
                break;
            case 2:
                parkingTicketManager.showTicketsInDB();
                break;
            case 3:
              again:  while(true) {
                    try {
                        parkingTicketManager.createParkingTicket();
                        break;
                    } catch (NoSuchTypeOfVehicleException exception) {
                        System.out.println(exception);
                        System.out.println("Try again: ");
                        continue again;
                    }
                }
                break;
            case 4:
                parkingTicketManager.removeParkingTicket();
                break;
            case 5:
                System.exit(0);
        }

    }

    public static void main(String[] args) {


        Controller controller = new Controller();

        while (true) {
            controller.chooseOption();

        }
    }

}