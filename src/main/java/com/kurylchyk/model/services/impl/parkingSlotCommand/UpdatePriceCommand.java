package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;

public class UpdatePriceCommand implements Command<Void> {

    private ParkingSlotDAO parkingSlotDAO;
    private Map<SlotSize,Integer> prices;
    private static final Logger logger = LogManager.getLogger(UpdatePriceCommand.class);

    public UpdatePriceCommand(Map<SlotSize,Integer> prices){
        this.prices = prices;
        parkingSlotDAO = new ParkingSlotDAO();
    }

    UpdatePriceCommand(Map<SlotSize,Integer> prices,ParkingSlotDAO parkingSlotDAO){
        this.prices = prices;
        this.parkingSlotDAO = parkingSlotDAO;
    }

    @Override
    public Void execute() throws ParkingSystemException {

        for(Map.Entry<SlotSize,Integer> price: prices.entrySet()){
            parkingSlotDAO.updatePrice(price.getKey(),price.getValue());
        }
        logger.info("Prices were updated");
        return null;
    }
}
