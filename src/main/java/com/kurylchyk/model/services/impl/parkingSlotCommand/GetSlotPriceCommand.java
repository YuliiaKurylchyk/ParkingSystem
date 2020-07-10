package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.parkingSlotDTOs.ParkingSlotPriceDTO;
import com.kurylchyk.model.services.impl.Command;

import java.util.List;

public class GetSlotPriceCommand implements Command<List<ParkingSlotPriceDTO>> {
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();

    @Override
    public List<ParkingSlotPriceDTO> execute() throws ParkingSystemException {

        return parkingSlotDAO.getSlotsPrice();
    }
}
