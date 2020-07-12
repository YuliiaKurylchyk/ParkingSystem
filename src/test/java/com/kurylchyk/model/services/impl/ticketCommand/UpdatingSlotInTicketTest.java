package com.kurylchyk.model.services.impl.ticketCommand;

import static org.junit.jupiter.api.Assertions.*;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@DisplayName("Should update parking ticket with new parking slot")
public class UpdatingSlotInTicketTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    ParkingTicketDAO parkingTicketDAO = mock(ParkingTicketDAO.class);

    @InjectMocks
    UpdateSlotInTicketCommand command;

    private ParkingTicket parkingTicket;
    private ParkingSlot newParkingSlot;

    @BeforeEach
    public void init() {
        Customer customer = Customer.newCustomer()
                .setCustomerID(1).setName("Name")
                .setSurname("Surname").setPhoneNumber("+38093104694").buildCustomer();

        Vehicle vehicle = new Car("Honda", "Crosstour", "BK 7956 IO", CarSize.EXCLUSIVE_CAR);

        ParkingSlot parkingSlot = new ParkingSlot(1, SlotSize.MEDIUM, SlotStatus.OCCUPIED, 15);

        parkingTicket = ParkingTicket.newParkingTicket()
                .withParkingTicketID(1)
                .withCustomer(customer)
                .withVehicle(vehicle)
                .withParkingSlot(parkingSlot)
                .withStatus(Status.PRESENT)
                .withArrivalTime(LocalDateTime.now().minusDays(5))
                .buildTicket();
    }


    @Test
    @DisplayName("Should update parking ticket with parking slot")
    public void shouldUpdateTicketWithSlot() {

        newParkingSlot = new ParkingSlot(4, SlotSize.MEDIUM, SlotStatus.VACANT);
        command = new UpdateSlotInTicketCommand(parkingTicket, newParkingSlot, parkingTicketDAO);

        doAnswer(new Answer<Void>() {

            public Void answer(InvocationOnMock invocation) {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length > 1 && arguments[0] != null && arguments[1] != null) {
                    ParkingTicket pk = (ParkingTicket) arguments[0];
                    ParkingSlot ps = (ParkingSlot) arguments[1];
                    pk.setParkingSlot(ps);
                }
                return null;
            }
        }).when(parkingTicketDAO).updateParkingSlotID(parkingTicket, newParkingSlot);

    }
}
