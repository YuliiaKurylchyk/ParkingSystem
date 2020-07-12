package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GettingVehicleTest {

    @Mock
    private VehicleDAO vehicleDAO;

    @InjectMocks
    private GetVehicleCommand command;

    private VehicleType expectedType = VehicleType.CAR;
    private Vehicle expectedVehicle;
    private String correctLicensePlate;
    private String wrongLicensePlate;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        correctLicensePlate = "BK 0430 OI";
        expectedVehicle = new Car("Honda","HR-V",correctLicensePlate, CarSize.SUV);
        wrongLicensePlate = "AA 0921 OP";
        when(vehicleDAO.select(correctLicensePlate)).thenReturn(Optional.ofNullable(expectedVehicle));
        when(vehicleDAO.select(wrongLicensePlate)).thenThrow(NoSuchVehicleFoundException.class);

    }
    @Test
    @DisplayName("Should select vehicle")
    public void shouldSelectVehicle() throws ParkingSystemException {

        command  = new GetVehicleCommand(correctLicensePlate,expectedType,vehicleDAO);
        Vehicle selectedVehicle = command.execute();

        assertAll(
                ()->assertNotNull(selectedVehicle),
                ()-> assertEquals(expectedVehicle,selectedVehicle)
        );

        verify(vehicleDAO).select(correctLicensePlate);
    }


    @Test
    @DisplayName("Should NOT select vehicle,but throw exception")
    public void shouldNotSelectVehicle(){

        command  = new GetVehicleCommand(wrongLicensePlate,expectedType,vehicleDAO);

        assertThrows(NoSuchVehicleFoundException.class,()->command.execute());

        verify(vehicleDAO).select(wrongLicensePlate);
    }



}
