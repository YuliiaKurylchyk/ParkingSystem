package com.kurylchyk.model.services.impl.ticketCommand;

import com.kurylchyk.model.dao.ParkingTicketDAO;
import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.Motorbike;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.ParkingSlotService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RemovingTicketTest {

    @Mock
    ParkingTicketDAO parkingTicketDAO;

    @Mock
    ParkingSlotService parkingSlotService;

    @InjectMocks
    RemoveParkingTicketCommand command;



    private static ParkingTicket parkingTicket;
    private static Customer customer;
    private static Vehicle vehicle;
    private static ParkingSlot parkingSlot;
    private static BigDecimal expectedPrice;


    @BeforeAll
    public static void initAll(){

        customer = Customer.newCustomer()
                .setCustomerID(1).setName("Name")
                .setSurname("Surname").setPhoneNumber("+380931092894").buildCustomer();

        vehicle = new Motorbike("Harley Davidson","Iron 883","BK 3242 IO");

        parkingSlot = new ParkingSlot(1, SlotSize.MEDIUM, SlotStatus.OCCUPIED,15);

        parkingTicket = ParkingTicket.newParkingTicket()
                .withParkingTicketID(1)
                .withCustomer(customer)
                .withVehicle(vehicle)
                .withParkingSlot(parkingSlot)
                .withStatus(Status.PRESENT)
                .withArrivalTime(LocalDateTime.now().minusDays(5))
                .buildTicket();
    }



    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);}



    @Test
    @DisplayName("Should make ticket to become 'Left' status")
    public void shouldRemoveParkingTicket() throws ParkingSystemException {

        command = new RemoveParkingTicketCommand(parkingTicket,parkingTicketDAO,parkingSlotService);

        expectedPrice = new BigDecimal(parkingSlot.getPrice()*5);

        when(parkingSlotService.updateStatus(parkingTicket.getParkingSlot(),SlotStatus.VACANT))
                .thenAnswer(i-> changeStatus(parkingSlot));
        doNothing().when(parkingTicketDAO).update(parkingTicket,parkingTicket.getParkingTicketID());

        ParkingTicket returnedTicket = command.execute();

        assertAll(
                ()->assertSame(parkingTicket,returnedTicket),
                ()->assertNotNull(returnedTicket),
                ()->assertEquals(returnedTicket.getParkingSlot().getStatus(),SlotStatus.VACANT),
                ()->assertEquals(parkingTicket.getParkingTicketID(),returnedTicket.getParkingTicketID()),
                ()-> assertNotNull(returnedTicket.getDepartureTime()),
                ()-> assertEquals(expectedPrice,returnedTicket.getCost()),
                ()->assertTrue(returnedTicket.getStatus()==Status.LEFT)
        );

        verify(parkingTicketDAO).update(parkingTicket,parkingTicket.getParkingTicketID());
        verify(parkingSlotService).updateStatus(parkingSlot,SlotStatus.VACANT);

    }

    private ParkingSlot changeStatus(ParkingSlot parkingSlot){

        parkingSlot.setStatus(SlotStatus.VACANT);
        return parkingSlot;
    }
}
