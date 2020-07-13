package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VehicleIsPresentTest {

    @Mock
    private VehicleDataUtil vehicleDataUtil;

    @InjectMocks
    private VehicleIsPresentCommand command;


    private String presentLicensePlate;
    private String unknownLicensePlate ;



    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        presentLicensePlate = "BK 0300 IM";
        unknownLicensePlate = "CE 4242 OP";

        when(vehicleDataUtil.isPresent(presentLicensePlate)).thenReturn(true);
        when(vehicleDataUtil.isPresent(unknownLicensePlate)).thenReturn(false);
    }


    @Test
    @DisplayName("Should be present ")
    public void shouldBePresent() throws ParkingSystemException {

        command = new VehicleIsPresentCommand(presentLicensePlate,vehicleDataUtil);
        Boolean result = command.execute();
        assertTrue(result);

        verify(vehicleDataUtil).isPresent(presentLicensePlate);

    }

    @Test
    @DisplayName("Should NOT be present")
    public void shouldNotBePresent() throws ParkingSystemException {
        command = new VehicleIsPresentCommand(unknownLicensePlate,vehicleDataUtil);
        Boolean result = command.execute();
        assertFalse(result);
        verify(vehicleDataUtil).isPresent(unknownLicensePlate);
    }
}
