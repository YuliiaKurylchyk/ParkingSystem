package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GettingByVehicleTest {

    @Mock
    ParkingTicketDAO parkingTicketDAO = mock(ParkingTicketDAO.class);

    @InjectMocks
    GetByVehicleCommand command;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private ParkingTicket expectedParkingTicket;
    private Vehicle givenVehicle;

    @BeforeEach
    public void initParkingTicket() {
        expectedParkingTicket = new ParkingTicket();
        givenVehicle = new Motorbike("Harley Davidson", "Iron 883","AA 4292 IA");
        expectedParkingTicket.setVehicle(givenVehicle);
    }

    @Test
    @DisplayName("Should get parking ticket")
    public void shouldGetParkingTicket() throws NoSuchParkingTicketException {


        command = new GetByVehicleCommand(givenVehicle.getLicensePlate(), parkingTicketDAO);

        when(parkingTicketDAO.selectByVehicleID(givenVehicle.getLicensePlate()))
                .thenReturn(Optional.ofNullable(expectedParkingTicket));

        ParkingTicket selectedParkingTicket = command.execute();

        assertAll(
                () -> assertNotNull(selectedParkingTicket),
                () -> assertEquals(givenVehicle, selectedParkingTicket.getVehicle())
        );

        verify(parkingTicketDAO).selectByVehicleID(givenVehicle.getLicensePlate());
    }


    @Test
    @DisplayName("Should throw exception because parking ticket does not exist")
    public void shouldThrowException()  {

        givenVehicle.setLicensePlate("AA 3241 OI");
        command = new GetByVehicleCommand(givenVehicle.getLicensePlate(), parkingTicketDAO);
        when(parkingTicketDAO.selectByVehicleID(givenVehicle.getLicensePlate())).thenThrow(NoSuchParkingTicketException.class);

        assertThrows(NoSuchParkingTicketException.class, () -> command.execute());

        verify(parkingTicketDAO).selectByVehicleID(givenVehicle.getLicensePlate());
    }

}
