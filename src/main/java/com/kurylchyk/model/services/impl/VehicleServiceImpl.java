package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.VehicleService;
import com.kurylchyk.model.services.impl.utilVehicle.VehicleCreator;
import com.kurylchyk.model.services.impl.vehicleCommand.*;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;

import java.util.List;


public class VehicleServiceImpl implements VehicleService {

    private CommandExecutor executor = new CommandExecutor();


    @Override
    public Vehicle create(VehicleCreator vehicleInfo)
            throws ParkingSystemException {
        Vehicle vehicle = null;
        if (executor.execute(new VehicleIsPresentCommand(vehicleInfo.getLicensePlate()))) {
            if (executor.execute(new CheckVehicleHasDeparturedCommand(vehicleInfo.getLicensePlate()))) {
                vehicle = getFromDB(vehicleInfo.getLicensePlate(), vehicleInfo.getVehicleType());
            }
        } else {
            vehicle = vehicleInfo.createVehicle();
        }
        return vehicle;
    }

    @Override
    public Vehicle find(String licensePlate) throws ParkingSystemException {
        VehicleType currentType = executor.execute(new GetVehicleTypeCommand(licensePlate));
        Vehicle vehicle = getFromDB(licensePlate, currentType);
        return vehicle;
    }

    @Override
    public Vehicle getFromDB(String licencePlate, VehicleType typeOfVehicle) throws ParkingSystemException {
        return executor.execute(new GetVehicleCommand(licencePlate, typeOfVehicle));
    }

    @Override
    public Vehicle saveToDB(Vehicle vehicle) throws ParkingSystemException {
        if (!isPresent(vehicle.getLicensePlate())) {
            executor.execute(new SaveVehicleCommand(vehicle));
        }
        return vehicle;
    }

    @Override
    public Vehicle update(VehicleCreator vehicleInfo, String licencePlate) throws ParkingSystemException {
        return executor.execute(new UpdateVehicleCommand(vehicleInfo, licencePlate));
    }

    @Override
    public Vehicle deleteCompletely(Vehicle vehicle) throws ParkingSystemException {
        executor.execute(new DeleteVehicleCommand(vehicle));
        return vehicle;
    }

    @Override
    public boolean isPresent(String licencePlate) throws ParkingSystemException {
        return executor.execute(new VehicleIsPresentCommand(licencePlate));
    }

    /*
    @Override
    public Status getVehicleStatus(String licencePlate) throws Exception {
        return executor.execute(new VehicleStatusCommand(licencePlate));
    }

     */

    @Override
    public List<Vehicle> getAll(VehicleType vehicleType) throws ParkingSystemException {
        return executor.execute(new GetAllVehiclesCommand(vehicleType));
    }


    @Override
    public List<Vehicle> getAll(Status status, VehicleType vehicleType) throws ParkingSystemException {
        return executor.execute(new GetAllVehiclesCommand(status, vehicleType));
    }

    @Override
    public void connectCustomerToVehicle(Vehicle vehicle, Customer customer) throws ParkingSystemException {
        executor.execute(new ConnectCustomerToVehicleCommand(vehicle, customer));
    }


    @Override
    public Integer countAllPresent() throws ParkingSystemException {
        return executor.execute(new CountPresentVehiclesCommand());
    }

    @Override
    public Integer countAllPresent(VehicleType vehicleType) throws ParkingSystemException {
        return executor.execute(new CountPresentVehiclesCommand(vehicleType));
    }


}
