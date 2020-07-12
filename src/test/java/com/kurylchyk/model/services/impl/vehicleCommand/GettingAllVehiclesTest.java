package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.Bus;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GettingAllVehiclesTest {

    @Mock
    private VehicleDAO vehicleDAO;

    @InjectMocks
    private GetAllVehiclesCommand command;

    private VehicleType expectedVehicleType = VehicleType.MOTORBIKE;

    private  List<Vehicle> vehiclesWithPresentStatus;
    private  List<Vehicle> allVehicles;

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);
        vehiclesWithPresentStatus = new ArrayList<>(Arrays.asList(
                new Motorbike("Harley Davidson","Iron 883","AA 9248 KS"),
                new Motorbike("Suzuki","GFX-R 1000","CE 9340 KP")));

        allVehicles= new ArrayList<>(Arrays.asList(
                new Motorbike("Harley Davidson","Iron 883","AA 9248 KS"),
                new Motorbike("BMW","F 850 GS","BK 9249 LK"),
                new Motorbike("Suzuki","GFX-R 1000","CE 9340 KP")));

        when(vehicleDAO.selectAll(Status.PRESENT)).thenReturn(vehiclesWithPresentStatus);
        when(vehicleDAO.selectAll()).thenReturn(allVehicles);
    }



    @Test
    @DisplayName("Should get vehicles that are present")
    public void shouldGetPresentVehicles() throws ParkingSystemException {
        command = new GetAllVehiclesCommand(Status.PRESENT,expectedVehicleType,vehicleDAO);
        List<Vehicle> selectedVehicle  =command.execute();

        assertAll(
                ()->assertNotNull(selectedVehicle),
                ()->assertTrue(selectedVehicle.size()<=allVehicles.size()),
                ()->assertTrue(allVehicles.containsAll(selectedVehicle))
        );

        verify(vehicleDAO).selectAll(Status.PRESENT);
        verify(vehicleDAO,never()).selectAll();
    }


    @Test
    @DisplayName("Should get all vehicles")
    public void shouldGetAllVehicles() throws ParkingSystemException {
        command = new GetAllVehiclesCommand(null,expectedVehicleType,vehicleDAO);
        List<Vehicle> selectedVehicle  =command.execute();

        assertAll(
                ()->assertNotNull(selectedVehicle),
                ()->assertEquals(selectedVehicle,allVehicles),
                ()->assertTrue(selectedVehicle.size()>vehiclesWithPresentStatus.size())
        );

        verify(vehicleDAO,never()).selectAll(any(Status.class));
        verify(vehicleDAO).selectAll();
    }



}
