package com.kurylchyk.model.services.impl.vehicleCommand;
import com.kurylchyk.model.dao.VehicleDAO;
import com.kurylchyk.model.parkingTicket.Status;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.services.impl.ticketCommand.GetAllTicketCommand;
import com.kurylchyk.model.vehicles.Vehicle;
import java.util.List;

public class GetAllVehiclesCommand implements Command<List<Vehicle>> {
    private Status status;
    private VehicleDAO vehicleDAO = new VehicleDAO();


    public GetAllVehiclesCommand() {};

    public GetAllVehiclesCommand(Status status) {
        this.status = status;
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
