package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.vehicles.Car;
import com.kurylchyk.model.vehicles.CarSize;
import com.kurylchyk.model.vehicles.VehicleType;

public class CarInfo  extends VehicleInfo {
    private CarSize carSize;

    public CarInfo(String make, String model, String licensePlate,CarSize carSize) {
        this.make = make;
        this.model = model;
        this.licensePlate = licensePlate;
        this.vehicleType = VehicleType.CAR;
        this.carSize = carSize;
    }

    @Override
    public Car createVehicle() {
        return new Car(make,model,licensePlate,carSize);
    }
}
