package com.kurylchyk.model.services.impl.vehicleCommand;

import com.kurylchyk.model.dao.VehicleDataUtil;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.nio.channels.OverlappingFileLockException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("Setting customer_id to vehicle in db")
public class ConnectCustomerToVehicleTest {

    @Mock
    private VehicleDataUtil vehicleDataUtil;

    @InjectMocks
    private ConnectCustomerToVehicleCommand command;

    private Vehicle vehicle;
    private Customer customer;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        vehicle = new Motorbike("Harley Davidson", "Iron 883", "AK 2301 IO");
        customer = Customer.newCustomer().setCustomerID(1).setName("Name").setSurname("Surname").setPhoneNumber("+380931092546").buildCustomer();

    }

    @Test
    @DisplayName("Should initialize customer id in vehicle table in database")
    public void shouldConnectCustomerToVehicle() throws ParkingSystemException {

        doAnswer((args)->{
                Object[] arguments = args.getArguments();
                if (arguments != null && arguments.length == 2 && arguments[0] != null && arguments[1] != null) {
                    String licensePlate = (String) arguments[0];
                    Integer customerID = (Integer) arguments[1];
                    assertAll(
                            () -> assertNotNull(licensePlate),
                            () -> assertNotNull(customerID)
                    );
                }
                return null;
            }).when(vehicleDataUtil).updateCustomerID(vehicle.getLicensePlate(), customer.getCustomerID());

        command = new ConnectCustomerToVehicleCommand(vehicle,customer,vehicleDataUtil);
        command.execute();

        verify(vehicleDataUtil).updateCustomerID(vehicle.getLicensePlate(),customer.getCustomerID());

    }


}
