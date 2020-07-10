package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.services.impl.Command;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefineParkingSlotCommand implements Command<SlotSize> {

    private Vehicle vehicle;
    private static final Logger logger = LogManager.getLogger(DefineParkingSlotCommand.class);

   public DefineParkingSlotCommand(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    @Override
    public SlotSize execute() throws ParkingSystemException {
        SlotSize slotSize = ParkingSlotDefiner.defineSlotSize(vehicle);
        logger.debug("Defined "+slotSize + " size for vehicle");
        return slotSize;
    }
}
