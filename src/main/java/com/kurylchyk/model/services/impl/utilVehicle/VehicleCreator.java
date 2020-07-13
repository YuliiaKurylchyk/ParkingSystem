package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.domain.vehicles.Vehicle;

/**
 * Used to identify appropriate vehicle type
 * and create this vehicle
 */
public abstract class VehicleCreator {

    protected  String model;
    protected  String make;
    protected  String licensePlate;
    protected VehicleType vehicleType;

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public abstract Vehicle createVehicle();

}
