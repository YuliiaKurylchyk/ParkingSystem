package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.exceptions.NoSuchVehicleFoundException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
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

public class GettingVehicleTypeTest {

    @Mock
    private VehicleDataUtil vehicleDataUtil;

    @InjectMocks
    private GetVehicleTypeCommand command;

    private Vehicle vehicle;
    private String wrongLicensePlate;


    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        vehicle = new Car("BMW","X5","AA 2349 OI", CarSize.SUV);
        wrongLicensePlate = "CE 2032 OL";
        when(vehicleDataUtil.getType(vehicle.getLicensePlate()))
                .thenReturn(Optional.ofNullable(vehicle.getVehicleType()));
        when(vehicleDataUtil.getType(wrongLicensePlate))
                .thenReturn(Optional.ofNullable(null));
    }

    @Test
    @DisplayName("Should get type of vehicle")
    public void shouldGetType() throws ParkingSystemException {


        command = new GetVehicleTypeCommand(vehicle.getLicensePlate(),vehicleDataUtil);
        VehicleType vehicleType = command.execute();

        assertAll(
                ()->  assertNotNull(vehicleType),
                ()->assertEquals(vehicle.getVehicleType(),vehicleType)
        );

        verify(vehicleDataUtil).getType(vehicle.getLicensePlate());

    }

    @Test
    @DisplayName("Should NOT get type, but throw an exception")
    public void shouldNotGetType() {

        command = new GetVehicleTypeCommand(wrongLicensePlate,vehicleDataUtil);
        assertThrows(NoSuchVehicleFoundException.class,()->command.execute());
        verify(vehicleDataUtil).getType(wrongLicensePlate);

    }
}
