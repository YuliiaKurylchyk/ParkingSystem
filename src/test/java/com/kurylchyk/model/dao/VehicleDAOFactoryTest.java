package com.kurylchyk.model.dao;

import com.kurylchyk.model.dao.vehicles.*;

import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleDAOFactoryTest {

    private VehicleType givenType;


    @Test
    @DisplayName("Should return motorbike dao")
    public void shouldReturnMotorbikeDAO(){

        givenType = VehicleType.MOTORBIKE;

        VehicleDAO selectedDAO = VehicleDAOFactory.getVehicleDAO(givenType);

        assertAll(
                ()->assertNotNull(selectedDAO),
                ()->  assertTrue(selectedDAO instanceof MotorbikeDAO)
        );
    }

    @Test
    @DisplayName("Should return car dao")
    public void shouldReturnCarDAO(){

        givenType = VehicleType.CAR;

        VehicleDAO selectedDAO = VehicleDAOFactory.getVehicleDAO(givenType);

        assertAll(
                ()->assertNotNull(selectedDAO),
                ()->  assertTrue(selectedDAO instanceof CarDAO)
        );
    }

    @Test
    @DisplayName("Should return truck dao")
    public void shouldReturnTruckDAO(){

        givenType = VehicleType.TRUCK;

        VehicleDAO selectedDAO = VehicleDAOFactory.getVehicleDAO(givenType);

        assertAll(
                ()->assertNotNull(selectedDAO),
                ()->  assertTrue(selectedDAO instanceof TruckDAO)
        );
    }

    @Test
    @DisplayName("Should return bus dao")
    public void shouldReturnBusDAO(){

        givenType = VehicleType.BUS;

        VehicleDAO selectedDAO = VehicleDAOFactory.getVehicleDAO(givenType);

        assertAll(
                ()->assertNotNull(selectedDAO),
                ()->  assertTrue(selectedDAO instanceof BusDAO)
        );
    }
}
