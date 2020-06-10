package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.services.impl.Command;

import java.util.ArrayList;
import java.util.List;

public class ValidateVehicleCommand  implements Command<List<String>> {

    private String make;
    private String model;
    private String licencePlate;
    private String typeOfVehicle;

    public  ValidateVehicleCommand(String make, String model, String licencePlate, String typeOfVehicle) {
        this.make = make;
        this.model = model;
        this.licencePlate = licencePlate;
        this.typeOfVehicle = typeOfVehicle;
    }

    @Override
    public List<String> execute() throws Exception {
        List<String> violations = new ArrayList<>();

        String regex = "^[A-Z]{2} [0-9]{4} [A-Z]{2}$";
        if (typeOfVehicle== null) {
            violations.add("Choose the type of vehicle");
        }

        if (licencePlate.isEmpty() || !licencePlate.matches(regex)) {

            violations.add("Bad format of licence plate. Try again");
        }

        if (make.isEmpty() || model.isEmpty()) {
            violations.add("Neither make nor model can be empty");
        }
        return violations;
    }
}
