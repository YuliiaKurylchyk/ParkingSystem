package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.vehicles.VehicleDAO;
import com.kurylchyk.model.domain.vehicles.Bus;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.impl.utilVehicle.BusCreator;
import com.kurylchyk.model.services.impl.utilVehicle.VehicleCreator;
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

public class UpdatingVehicleTest {


    @Mock
    private VehicleDAO vehicleDAO;

    @InjectMocks
    private UpdateVehicleCommand command;

    private Bus vehicle;

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);
        vehicle = new Bus("Mercedes-Benz","Sprinter","BO 4292 PO");
    }

    @Test
    @DisplayName("Should update customer")
    public void shouldUpdateCustomer() throws ParkingSystemException {

        VehicleCreator vehicleCreator = new BusCreator(vehicle.getMake(),
                vehicle.getModel(),vehicle.getLicensePlate(),vehicle.getCountOfSeats());


        command = new UpdateVehicleCommand(vehicleCreator,vehicle.getLicensePlate(),vehicleDAO);
        doAnswer( (args)-> {
                Object[] arguments = args.getArguments();
                if (arguments != null && arguments.length == 2 && arguments[0] != null && arguments[1] != null) {
                    Vehicle v = (Vehicle) arguments[0];
                    String lp = (String) arguments[1];

                    assertAll(
                            ()->  assertNotNull(v),
                            ()->assertNotNull(lp)
                    );
                }
                return null;
            }).when(vehicleDAO).update(vehicle,vehicle.getLicensePlate());

        command.execute();
        verify(vehicleDAO).update(vehicle,vehicle.getLicensePlate());
    }


}
