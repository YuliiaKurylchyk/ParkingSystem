package com.kurylchyk.model.services;

import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SizeOfSlot;
import com.kurylchyk.model.vehicles.TypeOfVehicle;

public interface ParkingLotService {

    Integer getCountOfSlot(SizeOfSlot sizeOfSlot);

    ParkingSlot getParkingSlot(TypeOfVehicle typeOfVehicle) throws NoAvailableParkingSlotException;

     void setParkingSlotBack(ParkingSlot parkingSlot);
}
