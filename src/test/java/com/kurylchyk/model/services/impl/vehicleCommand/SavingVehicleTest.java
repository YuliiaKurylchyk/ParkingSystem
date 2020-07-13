package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SavingVehicleTest {

    @Mock
    private VehicleDAO vehicleDAO;

    @InjectMocks
    SaveVehicleCommand command;

    private  Vehicle vehicle;
    private String expectedLicensePlate;


    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);

        expectedLicensePlate = "AK 4293 IK";
        vehicle = new Car("Mercedes-Benz","E350","AK 4293 IK", CarSize.EXCLUSIVE_CAR);
    }



    @Test
    @DisplayName("Saving vehicle test")
    public void shouldSaveCustomer() throws ParkingSystemException {

        when(vehicleDAO.insert(vehicle)).thenReturn(expectedLicensePlate);
        command = new SaveVehicleCommand(vehicle,vehicleDAO);

        command.execute();

        verify(vehicleDAO).insert(vehicle);
    }

}
