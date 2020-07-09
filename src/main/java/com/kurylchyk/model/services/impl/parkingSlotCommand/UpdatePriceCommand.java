package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UpdatePriceCommand implements Command<Void> {

    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private List<ParkingSlotPriceDTO> prices;
    private static final Logger logger = LogManager.getLogger(UpdatePriceCommand.class);

    public UpdatePriceCommand(List<ParkingSlotPriceDTO> prices){
        this.prices = prices;
    }

    @Override
    public Void execute() throws ParkingSystemException {

        for(ParkingSlotPriceDTO price: prices){
            parkingSlotDAO.updatePrice(price.getSlotSize(),price.getPrice());
        }
        logger.info("Prices were updated");
        return null;
    }
}
