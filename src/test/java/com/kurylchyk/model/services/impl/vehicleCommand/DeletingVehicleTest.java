package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.vehicles.Bus;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class DeletingVehicleTest {

    @Mock
    private VehicleDAO vehicleDAO;

    @InjectMocks
    private DeleteVehicleCommand command;

    private Vehicle vehicle ;
    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        vehicle = new Bus("Mercedes-Benz","Sprinter","CE 0240 IO",10);

        doAnswer(new Answer<Void>() {

            public Void answer(InvocationOnMock invocation) {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length ==1  && arguments[0] != null) {
                    Vehicle v = (Vehicle) arguments[0];

                    assertAll(
                            ()-> assertNotNull(v),
                            ()->assertSame(vehicle,v)
                    );
                }
                return null;
            }
        }).when(vehicleDAO).delete(vehicle);

    }

    @Test
    @DisplayName("Should delete vehicle")
    public void shouldDelete() throws ParkingSystemException {
        command = new DeleteVehicleCommand(vehicle,vehicleDAO);
        command.execute();
        verify(vehicleDAO).delete(vehicle);

    }

}
