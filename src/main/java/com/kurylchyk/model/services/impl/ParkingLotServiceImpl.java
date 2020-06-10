package com.kurylchyk.model.services.impl;

import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.SizeOfSlot;
import com.kurylchyk.model.services.ParkingLotService;
import com.kurylchyk.model.vehicles.TypeOfVehicle;

public class ParkingLotServiceImpl  implements ParkingLotService {
    SlotFactory slotFactory = new SlotFactory();

    @Override
    public Integer getCountOfSlot(SizeOfSlot sizeOfSlot) {
       return slotFactory.getCountOfSlot(sizeOfSlot);
    }

    @Override
    public ParkingSlot getParkingSlot(TypeOfVehicle typeOfVehicle) throws NoAvailableParkingSlotException {
        return slotFactory.getAppropriateSlot(typeOfVehicle);
    }

    @Override
    public void setParkingSlotBack(ParkingSlot parkingSlot) {

        slotFactory.setParkingSlotBack(parkingSlot);
    }


}
