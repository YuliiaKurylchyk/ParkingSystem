package com.kurylchyk.model.service;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.parkingSlots.ParkingSlot;
import com.kurylchyk.model.parkingSlots.ParkingSlotFactory;
import com.kurylchyk.model.parkingSlots.SizeOfSlot;

import java.util.List;

public class ParkingSlotService implements ServiceGetUpdate<ParkingSlot,Integer>{

    private ParkingSlotFactory parkingSlotFactory = new ParkingSlotFactory();
    private ParkingSlotDAO parkingSlotDAO = new ParkingSlotDAO();

    @Override
    public ParkingSlot get(Integer parkingSlotID) throws Exception {
        return parkingSlotDAO.select(parkingSlotID).get();
    }

    @Override
    public List<ParkingSlot> getAll() {
        return parkingSlotDAO.selectAll();
    }

    @Override
    public void update(ParkingSlot parkingSlot, Integer quantity) {
        parkingSlotDAO.update(parkingSlot,quantity);


    }

    public void update(SizeOfSlot size, Integer quantity) {
        ParkingSlot parkingSlot = parkingSlotFactory.getParkingSlot(size);
        update(parkingSlot,quantity);
    }

    public Integer getPrice(SizeOfSlot size){
        return parkingSlotDAO.selectPrice(size);
    }

    public Integer getNumberOfSlot(SizeOfSlot size){
        return parkingSlotDAO.selectNumberOfSlot(size);
    }
}
