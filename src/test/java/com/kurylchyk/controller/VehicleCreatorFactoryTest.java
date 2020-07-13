package com.kurylchyk.controller;

import com.kurylchyk.controller.utils.VehicleCreatorFactory;
import com.kurylchyk.model.domain.vehicles.Bus;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Truck;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.services.impl.utilVehicle.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VehicleCreatorFactoryTest {

    @Mock
    HttpServletRequest request;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    @DisplayName("Should define motorbike creator with appropriate data")
    public void shouldDefineMotorbike() {
        Motorbike motorbike = new Motorbike("Harley Davidson", "Iron 883", "AA 3123 OI");
        when(request.getParameter("make")).thenReturn(motorbike.getMake());
        when(request.getParameter("model")).thenReturn(motorbike.getModel());
        when(request.getParameter("licensePlate")).thenReturn(motorbike.getLicensePlate());
        when(request.getParameter("typeOfVehicle")).thenReturn(motorbike.getVehicleType().toString());

        VehicleCreator vehicleInfo = VehicleCreatorFactory.getVehicleInformation(request);

        assertAll(
                () -> assertNotNull(vehicleInfo),
                () -> assertTrue(vehicleInfo instanceof MotorbikeCreator)
        );
        Motorbike createdMotorbike = ((MotorbikeCreator) vehicleInfo).createVehicle();
        assertEquals(motorbike,createdMotorbike);
    }

    @Test
    @DisplayName("Should define car creator with appropriate data")
    public void shouldDefineCar() {
        Car car = new Car("BMW", "X6", "AA 0000 OI", CarSize.SUV);
        when(request.getParameter("make")).thenReturn(car.getMake());
        when(request.getParameter("model")).thenReturn(car.getModel());
        when(request.getParameter("licensePlate")).thenReturn(car.getLicensePlate());
        when(request.getParameter("typeOfVehicle")).thenReturn(car.getVehicleType().toString());
        when(request.getParameter("carSize")).thenReturn(car.getCarSize().toString());

        VehicleCreator vehicleInfo = VehicleCreatorFactory.getVehicleInformation(request);

        assertAll(
                () -> assertNotNull(vehicleInfo),
                () -> assertTrue(vehicleInfo instanceof CarCreator)
        );
        Car createdCar  = ((CarCreator) vehicleInfo).createVehicle();
        assertEquals(car,createdCar);

        verify(request).getParameter("carSize");
    }

    @Test
    @DisplayName("Should define truck creator with appropriate data")
    public void shouldDefineTruck() {
        Truck truck = new Truck("Iveco", "Z", "AA 0000 OI", true);
        when(request.getParameter("make")).thenReturn(truck.getMake());
        when(request.getParameter("model")).thenReturn(truck.getModel());
        when(request.getParameter("licensePlate")).thenReturn(truck.getLicensePlate());
        when(request.getParameter("typeOfVehicle")).thenReturn(truck.getVehicleType().toString());
        when(request.getParameter("trailerPresent")).thenReturn(String.valueOf(truck.getTrailerPresent()));

        VehicleCreator vehicleInfo = VehicleCreatorFactory.getVehicleInformation(request);

        assertAll(
                () -> assertNotNull(vehicleInfo),
                () -> assertTrue(vehicleInfo instanceof TruckCreator)
        );
        Truck createdTruck  = ((TruckCreator) vehicleInfo).createVehicle();
        assertEquals(truck,createdTruck);

        verify(request).getParameter("trailerPresent");
    }


    @Test
    @DisplayName("Should define bus creator with appropriate data")
    public void shouldDefineBus() {
        Bus bus = new Bus("Mercedes-Benz", "Sprinter", "AA 0123 OI",45);
        when(request.getParameter("make")).thenReturn(bus.getMake());
        when(request.getParameter("model")).thenReturn(bus.getModel());
        when(request.getParameter("licensePlate")).thenReturn(bus.getLicensePlate());
        when(request.getParameter("typeOfVehicle")).thenReturn(bus.getVehicleType().toString());
        when(request.getParameter("countOfSeats")).thenReturn(String.valueOf(bus.getCountOfSeats()));

        VehicleCreator vehicleInfo = VehicleCreatorFactory.getVehicleInformation(request);

        assertAll(
                () -> assertNotNull(vehicleInfo),
                () -> assertTrue(vehicleInfo instanceof BusCreator)
        );
        Bus createdBus = ((BusCreator) vehicleInfo).createVehicle();
        assertEquals(bus,createdBus);

        verify(request).getParameter("countOfSeats");
    }



    @AfterEach
    public void  after(){
        verify(request).getParameter("make");
        verify(request).getParameter("model");
        verify(request).getParameter("licensePlate");
        verify(request).getParameter("typeOfVehicle");
    }
}
