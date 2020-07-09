package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.impl.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetSlotPriceCommand implements Command<List<ParkingSlotPriceDTO>> {
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();

    @Override
    public List<ParkingSlotPriceDTO> execute() throws ParkingSystemException {

        return parkingSlotDAO.getSlotsPrice();
    }
}
