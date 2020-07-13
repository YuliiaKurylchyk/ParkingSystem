package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.ParkingTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChangeParkingSlotTest{

    @Mock
    private ParkingSlotService parkingSlotService;

    @Mock
    ParkingTicketService parkingTicketService;


    @InjectMocks
    ChangeParkingSlotCommand command;

    private ParkingSlot givenParkingSlot;
    private ParkingSlot changedParkingSlot;
    private ParkingTicket givenParkingTicket;
    private ParkingTicket changedParkingTicket;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);


        givenParkingSlot = new ParkingSlot(1, SlotSize.SMALL, SlotStatus.OCCUPIED);
        changedParkingSlot = givenParkingSlot;
        changedParkingSlot.setStatus(SlotStatus.VACANT);

        givenParkingTicket = ParkingTicket.newParkingTicket().withParkingSlot(givenParkingSlot).buildTicket();

    }

    @Test
    @DisplayName("Should change parking slot in parking ticket")
    public void shouldChangeSlot() throws ParkingSystemException {
        command = new ChangeParkingSlotCommand(givenParkingTicket,changedParkingSlot,parkingSlotService,parkingTicketService);

       doAnswer(
                new Answer<ParkingSlot>() {

                    public ParkingSlot answer(InvocationOnMock invocation) {
                        Object[] arguments = invocation.getArguments();
                        if (arguments != null && arguments.length ==2 && arguments[0] != null && arguments[1] != null) {
                            ParkingSlot ps = (ParkingSlot) arguments[0];
                            SlotStatus status = (SlotStatus) arguments[1];
                            ps.setStatus(status);
                            return ps;} return null;}}).when(parkingSlotService).updateStatus(givenParkingTicket.getParkingSlot(),SlotStatus.VACANT);


        doAnswer(
                new Answer<ParkingSlot>() {

                    public ParkingSlot answer(InvocationOnMock invocation) {
                        Object[] arguments = invocation.getArguments();
                        if (arguments != null && arguments.length ==2 && arguments[0] != null && arguments[1] != null) {
                            ParkingSlot ps = (ParkingSlot) arguments[0];
                            SlotStatus status = (SlotStatus) arguments[1];
                            ps.setStatus(status);
                            return ps;} return null;}}).when(parkingSlotService).updateStatus(changedParkingSlot,SlotStatus.OCCUPIED);

        doAnswer(
                new Answer<ParkingSlot>() {

                    public ParkingSlot answer(InvocationOnMock invocation) {
                        Object[] arguments = invocation.getArguments();
                        if (arguments != null && arguments.length ==2 && arguments[0] != null && arguments[1] != null) {
                            ParkingTicket p = (ParkingTicket) arguments[0];
                            ParkingSlot ps = (ParkingSlot) arguments[1];
                            p.setParkingSlot(ps);
                            return ps;} return null;}}).when(parkingTicketService).updateParkingSlot
                (givenParkingTicket,changedParkingSlot);

      command.execute();

        InOrder inOrder = Mockito.inOrder(parkingSlotService);

     inOrder.verify(parkingSlotService).updateStatus(givenParkingSlot,SlotStatus.VACANT);
     inOrder.verify(parkingSlotService).updateStatus(changedParkingSlot,SlotStatus.OCCUPIED);

     verify(parkingTicketService).updateParkingSlot(givenParkingTicket,changedParkingSlot);

    }
}
