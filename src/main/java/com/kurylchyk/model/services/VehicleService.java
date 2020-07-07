package com.kurylchyk.model.services;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.services.impl.utilVehicle.VehicleInfo;
import com.kurylchyk.model.vehicles.VehicleType;
import com.kurylchyk.model.vehicles.Vehicle;
import  com.kurylchyk.model.parkingTicket.Status;

import java.util.List;

//add validate
public interface VehicleService {

    Vehicle create(VehicleInfo vehicleInfo) throws Exception;

    Vehicle find(String licensePlate) throws Exception;

    Vehicle getFromDB(String licencePlate, VehicleType typeOfVehicle) throws Exception;

    Vehicle saveToDB(Vehicle vehicle) throws Exception;

    Vehicle update(VehicleInfo vehicleInfo, String licencePlate) throws Exception;

    //мб цього не треба// просто викликати в delete ticket
    Vehicle deleteCompletely(Vehicle vehicle) throws Exception;

    boolean isPresent(String licencePlate) throws Exception;

    Status getVehicleStatus(String licencePlate) throws Exception;
    List<Vehicle> getAll(VehicleType vehicleType) throws Exception;
    List<Vehicle> getAll(Status status,VehicleType vehicleType) throws Exception;
    void connectCustomerToVehicle(Vehicle vehicle, Customer customer) throws Exception;
    Integer countAllPresent() throws Exception;
    Integer countAllPresent(VehicleType vehicleType) throws Exception;

}
