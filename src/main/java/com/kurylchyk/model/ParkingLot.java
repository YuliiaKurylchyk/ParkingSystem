package com.kurylchyk.model;

import com.kurylchyk.model.dao.ParkingSlotDao;
import com.kurylchyk.model.parkingSlots.*;
import com.kurylchyk.model.exceptions.NoAvailableParkingSlotException;
import com.kurylchyk.model.exceptions.NoSuchTypeOfVehicleException;
import com.kurylchyk.model.vehicles.*;
import com.sun.javafx.css.Size;

import java.util.HashMap;
import java.util.Map;


public class ParkingLot {

    private static ParkingSlotDao parkingSlotDao;
    private static Map<SizeOfSlot, Integer> numberOfSlot;

    static {
        parkingSlotDao = new ParkingSlotDao();
        numberOfSlot = new HashMap<>();
        getNumberOfSlots();
    }

    private static void getNumberOfSlots() {
        int numberOfSmallSlots = parkingSlotDao.selectNumberOfSlot(SizeOfSlot.SMALL);
        int numberOfMediumSlots = parkingSlotDao.selectNumberOfSlot(SizeOfSlot.MEDIUM);
        int numberOfLargeSlots = parkingSlotDao.selectNumberOfSlot(SizeOfSlot.LARGE);

        numberOfSlot.put(SizeOfSlot.SMALL, numberOfSmallSlots);
        numberOfSlot.put(SizeOfSlot.MEDIUM, numberOfMediumSlots);
        numberOfSlot.put(SizeOfSlot.LARGE, numberOfLargeSlots);
    }

    public static ParkingSlot getParkingSlot(Vehicle vehicle) throws NoAvailableParkingSlotException {

        ParkingSlot parkingSlot = null;

        switch (vehicle.getTypeOfVehicle()) {
            case MOTORBIKE:
                parkingSlot = getSmallSlot();
                break;
            case CAR:
                parkingSlot = getMediumSlot();
                break;
            case TRUCK:
            case BUS:
                parkingSlot = getLargeSlot();
                break;
        }
        return parkingSlot;
    }

    public static void setParkingSlotBack(ParkingSlot parkingSlot) {
        SizeOfSlot sizeOfSlot = parkingSlot.getSizeOfSlot();
        numberOfSlot.put(sizeOfSlot,numberOfSlot.get(sizeOfSlot)+1);
        parkingSlotDao.update(parkingSlot,numberOfSlot.get(sizeOfSlot));
    }

    private static void getExtraSlot(SizeOfSlot currentSlot, SizeOfSlot fromSlot, int countOfNeeded, int countOfReceived) {

        numberOfSlot.put(fromSlot, numberOfSlot.get(fromSlot) - countOfNeeded);
        parkingSlotDao.update(fromSlot, numberOfSlot.get(fromSlot));

        numberOfSlot.put(currentSlot, countOfReceived);
        parkingSlotDao.update(currentSlot, numberOfSlot.get(currentSlot) + countOfReceived);

    }

    private static SmallSlot getSmallSlot() throws NoAvailableParkingSlotException {

        if (numberOfSlot.get(SizeOfSlot.SMALL) == 0) {

            if (numberOfSlot.get(SizeOfSlot.MEDIUM) != 0) {
                getExtraSlot(SizeOfSlot.SMALL, SizeOfSlot.MEDIUM, 1, 2);

            } else if (numberOfSlot.get(SizeOfSlot.LARGE) != 0) {

                getExtraSlot(SizeOfSlot.SMALL, SizeOfSlot.LARGE, 1, 4);
            } else {
                throw new NoAvailableParkingSlotException("No available parking slots left");
            }
        }
        numberOfSlot.put(SizeOfSlot.SMALL, numberOfSlot.get(SizeOfSlot.SMALL) - 1);
        parkingSlotDao.update(SizeOfSlot.SMALL, numberOfSlot.get(SizeOfSlot.SMALL));
        return new SmallSlot();
    }

    private static MediumSlot getMediumSlot() throws NoAvailableParkingSlotException {
        if (numberOfSlot.get(SizeOfSlot.MEDIUM) == 0) {

            if (numberOfSlot.get(SizeOfSlot.SMALL) >= 2) {
                getExtraSlot(SizeOfSlot.MEDIUM,SizeOfSlot.SMALL,2,1);
            } else if (numberOfSlot.get(SizeOfSlot.LARGE) != 0) {

                getExtraSlot(SizeOfSlot.MEDIUM, SizeOfSlot.LARGE, 1, 2);
            } else {
                throw new NoAvailableParkingSlotException("No available parking slots left");
            }
        }
        numberOfSlot.put(SizeOfSlot.MEDIUM, numberOfSlot.get(SizeOfSlot.MEDIUM) - 1);
        parkingSlotDao.update(SizeOfSlot.MEDIUM, numberOfSlot.get(SizeOfSlot.MEDIUM));
        return new MediumSlot();
    }

    private static  LargeSlot getLargeSlot() throws NoAvailableParkingSlotException {

        if (numberOfSlot.get(SizeOfSlot.LARGE) == 0) {

            if (numberOfSlot.get(SizeOfSlot.SMALL) >= 4) {
                getExtraSlot(SizeOfSlot.LARGE,SizeOfSlot.SMALL,4,1);
            } else if (numberOfSlot.get(SizeOfSlot.MEDIUM) >=2 ) {

                getExtraSlot(SizeOfSlot.LARGE, SizeOfSlot.MEDIUM, 2, 1);
            } else {
                throw new NoAvailableParkingSlotException("No available parking slots left");
            }
        }
        numberOfSlot.put(SizeOfSlot.LARGE, numberOfSlot.get(SizeOfSlot.LARGE) - 1);
        parkingSlotDao.update(SizeOfSlot.LARGE, numberOfSlot.get(SizeOfSlot.LARGE));
        return new LargeSlot();

    }

}
