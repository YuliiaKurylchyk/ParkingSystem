package com.kurylchyk.model.services;

import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;

import java.util.List;

//add validate
public interface VehicleService {

    Vehicle create(String make,String model,String licencePlate,TypeOfVehicle typeOfVehicle) throws Exception;

    Vehicle getFromDB(String licencePlate) throws Exception;

    Vehicle saveToDB(Vehicle vehicle) throws Exception;

    Vehicle update(Vehicle vehicle, String licencePlate) throws Exception;

    //мб оцього не треба// просто викликати в delete ticket
    Vehicle deleteCompletely(Vehicle vehicle) throws Exception;

    boolean isPresent(String licencePlate) throws Exception;

    String getVehicleStatus(String licencePlate) throws Exception;

    void connectCustomerToVehicle(Vehicle vehicle, Customer customer) throws Exception;
    Integer countAllPresent(TypeOfVehicle typeOfVehicle) throws Exception;
    List<String> validate(String make, String model, String licencePlate, String typeOfVehicle) throws Exception;
}
