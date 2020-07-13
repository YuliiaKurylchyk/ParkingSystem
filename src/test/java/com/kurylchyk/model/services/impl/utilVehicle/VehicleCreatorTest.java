package com.kurylchyk.model.services.impl.utilVehicle;

import com.kurylchyk.model.domain.vehicles.Bus;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Truck;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Test of creating appropriate vehicles type")
public class VehicleCreatorTest {

    String expectedMake = "Make";
    String expectedModel = "Model";
    String expectedLicensePlate = "AA 0000 AA";

    @Test
    @DisplayName("Should create the car")
    public void shouldCreateCar() {

        CarSize expectedCarSize = CarSize.MPV;

        CarCreator carCreator = new CarCreator(expectedMake, expectedModel, expectedLicensePlate, expectedCarSize);

        Car createdCar = carCreator.createVehicle();

        assertAll(
                () -> assertNotNull(createdCar),
                () -> assertEquals(createdCar.getMake(), expectedMake),
                () -> assertEquals(createdCar.getModel(), expectedModel),
                () -> assertEquals(createdCar.getLicensePlate(), expectedLicensePlate),
                () -> assertEquals(createdCar.getCarSize(), expectedCarSize));
    }

    @Test
    @DisplayName("Should create the bus")
    public void shouldCreateBus() {

        Integer expectedCountOfSeats = 60;

        BusCreator busCreator = new BusCreator(expectedMake, expectedModel, expectedLicensePlate, expectedCountOfSeats);
        Bus createdBus = busCreator.createVehicle();

        assertAll(
                () -> assertNotNull(createdBus),
                () -> assertEquals(createdBus.getMake(), expectedMake),
                () -> assertEquals(createdBus.getModel(), expectedModel),
                () -> assertEquals(createdBus.getLicensePlate(), expectedLicensePlate),
                () -> assertEquals(createdBus.getCountOfSeats(), expectedCountOfSeats));
    }


    @Test
    @DisplayName("Should create the truck")
    public void shouldCreateTruck() {

       Boolean isTrailerPresent = false;

       TruckCreator truckCreator = new TruckCreator(expectedMake,expectedModel,expectedLicensePlate,isTrailerPresent);

       Truck createdTruck = truckCreator.createVehicle();
        assertAll(
                () -> assertNotNull(createdTruck),
                () -> assertEquals(createdTruck.getMake(), expectedMake),
                () -> assertEquals(createdTruck.getModel(), expectedModel),
                () -> assertEquals(createdTruck.getLicensePlate(), expectedLicensePlate),
                () -> assertEquals(createdTruck.getTrailerPresent(),isTrailerPresent));
    }


    @Test
    @DisplayName("Should create the motorbike")
    public void shouldCreateMotorbike() {


        MotorbikeCreator motorbikeCreator = new MotorbikeCreator(expectedMake,expectedModel,expectedLicensePlate);
        Motorbike createdMotorbike = motorbikeCreator.createVehicle();

        assertAll(
                () -> assertNotNull(createdMotorbike),
                () -> assertEquals(createdMotorbike.getMake(), expectedMake),
                () -> assertEquals(createdMotorbike.getModel(), expectedModel),
                () -> assertEquals(createdMotorbike.getLicensePlate(), expectedLicensePlate));
    }
}
