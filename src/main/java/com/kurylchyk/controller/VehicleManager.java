package com.kurylchyk.controller;

import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.vehicles.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VehicleManager {

    static Vehicle createVehicle(String make, String model, String licencePlate, TypeOfVehicle type) {

        Vehicle vehicle = Vehicle.newVehicle()
                .setType(type)
                .setMake(make)
                .setModel(model)
                .setLicencePlate(licencePlate)
                .buildVehicle();
        return vehicle;
    }

}
