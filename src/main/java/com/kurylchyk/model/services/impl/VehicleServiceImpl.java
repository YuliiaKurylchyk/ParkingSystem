package com.kurylchyk.model.services.impl;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.vehicleCommand.*;
import com.kurylchyk.model.customer.Customer;
import com.kurylchyk.model.vehicles.TypeOfVehicle;
import com.kurylchyk.model.vehicles.Vehicle;
import com.kurylchyk.model.parkingTicket.Status;
import java.util.List;


public class VehicleServiceImpl implements VehicleService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public Vehicle create(String make, String model, String licencePlate, TypeOfVehicle typeOfVehicle) throws Exception {

      return executor.execute(new CreateVehicleCommand(make,model,licencePlate,typeOfVehicle));
    }

    @Override
    public Vehicle getFromDB(String licencePlate) throws Exception {
       return executor.execute(new GetVehicleCommand(licencePlate));
    }

    @Override
    public Vehicle saveToDB(Vehicle vehicle) throws Exception {
        if(!isPresent(vehicle.getLicensePlate())) {
            executor.execute(new SaveVehicleCommand(vehicle));
        }
        return vehicle;
    }

    @Override
    public Vehicle update(Vehicle vehicle, String licencePlate) throws Exception {
         executor.execute(new UpdateVehicleCommand(vehicle,licencePlate));
         return  vehicle;

    }

    @Override
    public Vehicle deleteCompletely(Vehicle vehicle) throws Exception {
        executor.execute(new DeleteVehicleCommand(vehicle));
        return  vehicle;
    }

    @Override
    public boolean isPresent(String licencePlate) throws Exception {
        return executor.execute(new VehicleIsPresentCommand(licencePlate));
    }

    @Override
    public String getVehicleStatus(String licencePlate) throws Exception {
        return executor.execute(new VehicleStatusCommand(licencePlate));
    }

    @Override
    public List<Vehicle> getAll() throws Exception {
        return executor.execute(new GetAllVehiclesCommand());
    }


    @Override
    public List<Vehicle> getAll(Status status) throws Exception {
        return executor.execute(new GetAllVehiclesCommand(status));
    }

    @Override
    public void connectCustomerToVehicle(Vehicle vehicle, Customer customer) throws Exception {
         executor.execute(new ConnectCustomerToVehicleCommand(vehicle,customer));
    }

    @Override
    public Integer countAllPresent(TypeOfVehicle typeOfVehicle) throws Exception {
        return executor.execute(new CountVehiclePresentCommand(typeOfVehicle));
    }

    @Override
    public List<String> validate(String make, String model, String licencePlate, String typeOfVehicle) throws Exception {
        return executor.execute(new ValidateVehicleCommand(make,model,licencePlate,typeOfVehicle));
    }


}
