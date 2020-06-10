package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;

public class CreateVehicleCommand implements Command<Vehicle> {

    private String make;
    private String model;
    private String licencePlate;
    private TypeOfVehicle typeOfVehicle;

    public CreateVehicleCommand(String make, String model, String licencePlate, TypeOfVehicle typeOfVehicle) {

        this.make= make;
        this.model = model;
        this.licencePlate  = licencePlate;
        this.typeOfVehicle = typeOfVehicle;

    }

    @Override
    public Vehicle execute() throws Exception {
        Vehicle vehicle = Vehicle.newVehicle().setType(typeOfVehicle).setMake(make)
                .setModel(model).setLicencePlate(licencePlate).buildVehicle();
        return vehicle;
    }
}
