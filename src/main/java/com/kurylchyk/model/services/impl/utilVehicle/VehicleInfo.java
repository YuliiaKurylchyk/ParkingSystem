package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.vehicles.VehicleType;
import com.kurylchyk.model.vehicles.Vehicle;

public abstract class VehicleInfo {

    protected  String model;
    protected    String make;
    protected    String licensePlate;
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