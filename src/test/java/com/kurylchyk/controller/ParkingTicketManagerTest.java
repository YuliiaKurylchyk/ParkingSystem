package com.kurylchyk.controller;

import com.kurylchyk.model.vehicles.Car;
import com.kurylchyk.model.vehicles.Vehicle;
import org.junit.Test;

public class ParkingTicketManagerTest {

    private ParkingTicketManager parkingTicketManager = new ParkingTicketManager();


    @Test
    public void shouldReturnSameVehicle(){

        Vehicle vehicle = new Car("Honda","Crosstour","BK 0300 IM");

    }



}
