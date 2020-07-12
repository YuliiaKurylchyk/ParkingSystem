package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.CustomerDAO;
import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.exceptions.SuchVehiclePresentException;
import com.kurylchyk.model.services.impl.customerCommand.CheckCustomerIsPresentCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class VehicleHasDeparturedTest {

    @Mock
    private VehicleDataUtil vehicleDataUtil;

    @InjectMocks
    private CheckVehicleHasDeparturedCommand command;

    private String licensePlateOfPresentVehicle;
    private String licensePlateOfLeftVehicle;

    @BeforeEach
    public void init(){
        licensePlateOfPresentVehicle  = "BK 0300 OK";
        licensePlateOfLeftVehicle = "BK 0506 HK";
        MockitoAnnotations.initMocks(this);
        when(vehicleDataUtil.getStatus(licensePlateOfPresentVehicle)).thenReturn(Status.PRESENT);
        when(vehicleDataUtil.getStatus(licensePlateOfLeftVehicle)).thenReturn(Status.LEFT);
    }

    @Test
    @DisplayName("Should state that vehicle has already left")
    public void shouldBeLeft() throws ParkingSystemException {
        command = new CheckVehicleHasDeparturedCommand(licensePlateOfLeftVehicle,vehicleDataUtil);

        Boolean result = command.execute();
        assertTrue(result);
        verify(vehicleDataUtil).getStatus(licensePlateOfLeftVehicle);

    }

    @Test
    @DisplayName("Should state that vehicle is present on parking lot)")
    public void shouldBePresent()  {
        command = new CheckVehicleHasDeparturedCommand(licensePlateOfPresentVehicle,vehicleDataUtil);


        assertThrows(SuchVehiclePresentException.class,()->command.execute());
        verify(vehicleDataUtil).getStatus(licensePlateOfPresentVehicle);

    }

}
