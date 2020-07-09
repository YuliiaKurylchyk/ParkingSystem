package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.parkingSlots.SlotSize;
import com.kurylchyk.model.vehicles.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ParkingSlotDefiner {

    private static final Logger logger = LogManager.getLogger(ParkingSlotDefiner.class);
    public static SlotSize defineSlotSize(Vehicle vehicle) {

        VehicleType typeOfVehicle = vehicle.getVehicleType();
        logger.debug("Defining slot for "+typeOfVehicle);
        switch (typeOfVehicle){
            case MOTORBIKE: return defineForMotorbike();
            case CAR: return defineForCar( ((Car)vehicle).getCarSize());
            case TRUCK: return defineForTruck( ((Truck)vehicle).getTrailerPresent());
            case BUS: return defineForBus( ((Bus)vehicle).getCountOfSeats());
        }
        return null;
    }

    private static SlotSize defineForMotorbike(){

        return SlotSize.SMALL;
    }

    private static SlotSize defineForCar(CarSize carSize){

        if(carSize.equals(CarSize.MINI_CAR)) {
            return SlotSize.SMALL;
        }else if(carSize.equals(CarSize.PICK_UP)){
            return SlotSize.LARGE;
        }
        else return SlotSize.MEDIUM;

    }

    private static SlotSize defineForBus(Integer countOfSeats){

        if(countOfSeats<=17){
            return SlotSize.MEDIUM;
        }else{
           return SlotSize.LARGE;
        }
    }

    private static SlotSize defineForTruck(Boolean trailerPresent){

        if(!trailerPresent) {
            return SlotSize.MEDIUM;
        }else{
            return SlotSize.LARGE;
        }
    }

}
