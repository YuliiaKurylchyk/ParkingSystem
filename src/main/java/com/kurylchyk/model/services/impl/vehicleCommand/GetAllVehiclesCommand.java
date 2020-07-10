package com.kurylchyk.model.services.impl.vehicleCommand;
import com.kurylchyk.model.dao.VehicleDAOFactory;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.domain.vehicles.Vehicle;
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
    public List<Vehicle> execute() throws ParkingSystemException {
        List<Vehicle> allVehicles;
        if(status!=null) {
            allVehicles = vehicleDAO.selectAll(status);
        }else {
            allVehicles = vehicleDAO.selectAll();
        }
        return allVehicles;
    }
}
