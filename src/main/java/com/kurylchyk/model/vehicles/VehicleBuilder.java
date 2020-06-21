package com.kurylchyk.model.vehicles;

public class VehicleBuilder {
    private Vehicle vehicle;

    public VehicleBuilder setType(TypeOfVehicle type){

        switch (type) {
            case MOTORBIKE: vehicle =  new Motorbike(); break;
            case CAR: vehicle =  new Car();break;
            case TRUCK: vehicle =  new Truck(); break;
            case BUS:vehicle =  new Bus();break;
        }
        return this;
    }

    public  VehicleBuilder setMake(String make){
        vehicle.setMake(make);
        return this;
    }

    public  VehicleBuilder setModel(String model){
        vehicle.setModel(model);
        return this;
    }

    public  VehicleBuilder setLicencePlate(String licencePlate){
        vehicle.setLicensePlate(licencePlate);
        return this;
    }

    public   Vehicle buildVehicle(){
        return vehicle;
    }



}
