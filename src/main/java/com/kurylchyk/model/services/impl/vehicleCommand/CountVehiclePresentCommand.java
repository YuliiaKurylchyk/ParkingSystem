package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.vehicles.TypeOfVehicle;

public class CountVehiclePresentCommand implements Command<Integer> {
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private TypeOfVehicle typeOfVehicle;

    public CountVehiclePresentCommand(TypeOfVehicle typeOfVehicle){
        this.typeOfVehicle = typeOfVehicle;
    }


    @Override
    public Integer execute() throws Exception {
        return vehicleDAO.countAllPresent(typeOfVehicle);
    }
}
