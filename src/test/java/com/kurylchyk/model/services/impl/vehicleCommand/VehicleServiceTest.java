package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.Connector;

import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.VehicleServiceImpl;
import com.kurylchyk.model.domain.vehicles.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kurylchyk.model.services.impl.utilVehicle.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;



public class VehicleServiceTest {


    private VehicleService vehicleService = new VehicleServiceImpl();


    @BeforeAll
    public static void init() {
        new Connector();
    }

    @Test
    public void shouldCreateCar() throws ParkingSystemException {
        String make = "Honda";
        String model = "Crosstour";
        String licensePlate = "BK 0300 OK";
        CarSize carSize = CarSize.SUV;


        VehicleCreator vehicleInfo = new CarCreator(make, model, licensePlate, carSize);

        Car car = new Car(make, model, licensePlate, carSize);

        Car createdCar = (Car) vehicleService.create(vehicleInfo);
        assertEquals(car, createdCar);

    }


    @Test
    public void shouldCreateBus() throws ParkingSystemException {
        String make = "Mercedes-Benz";
        String model = "Sprinter";
        String licensePlate = "AA 5304 KS";
        Integer countOfSeats = 20;

        VehicleCreator vehicleInfo = new BusCreator(make, model, licensePlate, countOfSeats);

        Bus bus = new Bus(make, model, licensePlate, countOfSeats);
        Bus createdBus = (Bus) vehicleService.create(vehicleInfo);

        assertEquals(bus, createdBus);
    }

    @Test
    public void shouldCreateTruck() throws ParkingSystemException {
        String make = "Iveco";
        String model = "Z";
        String licensePlate = "AA 4324 CE";
        Boolean trailerPresent = true;

        VehicleCreator vehicleInfo = new TruckCreator(make, model, licensePlate, trailerPresent);

        Truck truck = new Truck(make, model, licensePlate, trailerPresent);

        Truck createdTruck = (Truck) vehicleService.create(vehicleInfo);
        assertEquals(truck, createdTruck);
    }

    @Test
    public void shouldCreateMotorbike() throws ParkingSystemException {
        String make = "Harley Davidson";
        String model = "Iron 883";
        String licensePlate = "CE 0012 AK";

        VehicleCreator vehicleInfo = new MotorbikeCreator(make, model, licensePlate);

        Motorbike motorbike = new Motorbike(make, model, licensePlate);

        Motorbike createdMotorbike = (Motorbike) vehicleService.create(vehicleInfo);
        assertEquals(motorbike, createdMotorbike);
    }


}
