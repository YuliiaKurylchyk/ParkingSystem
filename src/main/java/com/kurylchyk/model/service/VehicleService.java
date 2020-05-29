package com.kurylchyk.model.service;

import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.Vehicle;

import java.util.List;

public class VehicleService implements ServiceAddDelete<Vehicle,String>,ServiceGetUpdate<Vehicle,String>{

    private VehicleDAO vehicleDAO = new VehicleDAO();

    public String add(Vehicle vehicle) {
        return vehicleDAO.insert(vehicle);
    }

    public Vehicle get(String licencePlate) throws NoSuchVehicleFoundException{

        return vehicleDAO.select(licencePlate)
                .orElseThrow(()->new NoSuchVehicleFoundException("No vehicle with "+licencePlate + " found"));
    }

    @Override
    public List<Vehicle> getAll() {

      return   vehicleDAO.selectAll();
    }

    public void delete(Vehicle vehicle) {
        vehicleDAO.delete(vehicle);
    }

    public void  update(Vehicle updatedVehicle, String previousLicencePlate) {
        vehicleDAO.update(updatedVehicle, previousLicencePlate);
    }

    public boolean isPresent(String licencePlate) {
        return  vehicleDAO.select(licencePlate).isPresent();
    }

    public String getStatus(String licencePlate) {

        return vehicleDAO.getStatus(licencePlate);

    }

    public void updateCustomerID(String licencePlate, Integer customerID){

        vehicleDAO.updateCustomerID(licencePlate,customerID);
    }
}
