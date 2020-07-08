package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.impl.Command;

import java.util.List;

public class UpdatePriceCommand implements Command<Void> {

    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();
    private List<ParkingSlotPriceDTO> prices;

    public UpdatePriceCommand(List<ParkingSlotPriceDTO> prices){
        this.prices = prices;
    }

    @Override
    public Void execute() throws ParkingSystemException {

        for(ParkingSlotPriceDTO price: prices){
            parkingSlotDAO.updatePrice(price.getSlotSize(),price.getPrice());
        }

        return null;
    }
}
