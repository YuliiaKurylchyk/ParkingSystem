package com.kurylchyk.model.services.impl.vehicleCommand;
import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.vehicles.VehicleType;
import com.kurylchyk.model.vehicles.Vehicle;
import java.util.List;

public class GetAllVehiclesCommand implements Command<List<Vehicle>> {
    private Status status;
    private VehicleDAO vehicleDAO;


    public GetAllVehiclesCommand(VehicleType typeOfVehicle) {
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(typeOfVehicle);

    };

    public GetAllVehiclesCommand(Status status, VehicleType typeOfVehicle) {
        this.status = status;
        vehicleDAO = VehicleDAOFactory.getVehicleDAO(typeOfVehicle);
    }

    @Override
    public List<Vehicle> execute() throws Exception {
        List<Vehicle> allVehicles;
        if(status!=null) {
            allVehicles = vehicleDAO.selectAll(status);
        }else {
            allVehicles = vehicleDAO.selectAll();
        }
        return allVehicles;
    }
}
