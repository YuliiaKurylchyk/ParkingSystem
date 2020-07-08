package com.kurylchyk.model.services;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.utilVehicle.VehicleInfo;
import com.kurylchyk.model.vehicles.VehicleType;
import com.kurylchyk.model.vehicles.Vehicle;
import  com.kurylchyk.model.parkingTicket.Status;

import java.util.List;


public interface VehicleService {

    Vehicle create(VehicleInfo vehicleInfo) throws ParkingSystemException;

    Vehicle find(String licensePlate) throws ParkingSystemException;

    Vehicle getFromDB(String licencePlate, VehicleType typeOfVehicle) throws ParkingSystemException;

    Vehicle saveToDB(Vehicle vehicle) throws ParkingSystemException;

    Vehicle update(VehicleInfo vehicleInfo, String licencePlate) throws ParkingSystemException;

    //мб цього не треба// просто викликати в delete ticket
    Vehicle deleteCompletely(Vehicle vehicle) throws ParkingSystemException;

    boolean isPresent(String licencePlate) throws ParkingSystemException;

    List<Vehicle> getAll(VehicleType vehicleType) throws ParkingSystemException;
    List<Vehicle> getAll(Status status,VehicleType vehicleType) throws ParkingSystemException;
    void connectCustomerToVehicle(Vehicle vehicle, Customer customer) throws ParkingSystemException;
    Integer countAllPresent(VehicleType vehicleType) throws ParkingSystemException;
    Integer countAllPresent() throws ParkingSystemException;

}
