package com.kurylchyk.controller.validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class VehicleValidation implements Validation {

    private String make;
    private String model;
    private String licencePlate;
    private String typeOfVehicle;
    @Override
    public List<String> validate() {
        List<String> violations = new ArrayList<>();

        String regex = "^[A-Z]{2} [0-9]{4} [A-Z]{2}$";
        if (typeOfVehicle== null) {
            violations.add("Choose the type of vehicle");
        }

        if (!licencePlate.matches(regex)) {

            violations.add("Bad format of licence plate. Try again");
        }

        if (make.length() == 0 || model.length() == 0) {
            violations.add("Neither make nor model can be empty");
        }
        return violations;
    }

    private VehicleValidation(String make, String model, String licencePlate, String typeOfVehicle) {
        this.make = make;
        this.model = model;
        this.licencePlate = licencePlate;
        this.typeOfVehicle = typeOfVehicle;
    }

    @Override
    public void setAsRequestAttribute(HttpServletRequest req) {

        req.setAttribute("make", make);
        req.setAttribute("model", model);
        req.setAttribute("licencePlate", licencePlate);
        req.setAttribute("typeOfVehicle",typeOfVehicle);

    }

    public static VehicleValidation fromRequestParameters(
            HttpServletRequest request) {
        return new VehicleValidation(
                request.getParameter("make"),
                request.getParameter("model"),
                request.getParameter("licencePlate"),
                request.getParameter("typeOfVehicle"));
    }
}
