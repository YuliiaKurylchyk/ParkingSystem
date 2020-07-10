package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;

public class CarCreator extends VehicleCreator {
    private CarSize carSize;

    public CarCreator(String make, String model, String licensePlate, CarSize carSize) {
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
