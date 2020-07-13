package com.kurylchyk.model.services.impl.parkingSlotCommand;

import com.kurylchyk.model.dao.ParkingSlotDAO;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class CountAvailableSlotTest {

    @Mock
    private ParkingSlotDAO parkingSlotDAO;

    @InjectMocks
    private CountAvailableSlotCommand command;

    private static SlotSize slotSize;
    private static Integer expectedSmallSlots;
    private static Integer expectedMediumSlots;
    private static Integer expectedLargeSlots;



    @BeforeAll
    public static void initAll(){
        expectedSmallSlots = 15;
        expectedMediumSlots = 20;
        expectedLargeSlots = 40;
        slotSize = SlotSize.SMALL;
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should get count of any available slots")
    public void shouldAddNewSlot() throws ParkingSystemException {
        command = new CountAvailableSlotCommand(SlotSize.SMALL, parkingSlotDAO);

        when(parkingSlotDAO.countOfAvailable(any(SlotSize.class))).thenReturn(anyInt());
        Integer count = command.execute();

        assertNotNull(count);

        verify(parkingSlotDAO).countOfAvailable(any(SlotSize.class));
    }

    @Nested
    @DisplayName("Should count available slots with different size")
    class CountAppropriateSlots {
        @Test
        @DisplayName("Should count small slots")
        public void shouldCountSmallSlots() throws ParkingSystemException {
            command = new CountAvailableSlotCommand(slotSize.SMALL, parkingSlotDAO);

            when(parkingSlotDAO.countOfAvailable(SlotSize.SMALL)).thenReturn(expectedSmallSlots);
            Integer count = command.execute();
            assertAll(
                    () -> assertNotNull(count),
                    () -> assertEquals(expectedSmallSlots, count, () -> "Expected " + expectedSmallSlots + " but was " + count)
            );
            verify(parkingSlotDAO).countOfAvailable(SlotSize.SMALL);
        }


        @Test
        @DisplayName("Should count medium slots")
        public void shouldCountMediumSlots() throws ParkingSystemException {

            command = new CountAvailableSlotCommand(slotSize.MEDIUM, parkingSlotDAO);
            when(parkingSlotDAO.countOfAvailable(SlotSize.MEDIUM)).thenReturn(expectedMediumSlots);
            Integer count = command.execute();
            assertAll(
                    () -> assertNotNull(count),
                    () -> assertEquals(expectedMediumSlots, count, () -> "Expected " + expectedMediumSlots + " but was " + count)
            );
            verify(parkingSlotDAO).countOfAvailable(SlotSize.MEDIUM);
        }


        @Test
        @DisplayName("Should count large slots")
        public void shouldCountLargeSlots() throws ParkingSystemException {

            command = new CountAvailableSlotCommand(slotSize.LARGE, parkingSlotDAO);
            when(parkingSlotDAO.countOfAvailable(SlotSize.LARGE)).thenReturn(expectedLargeSlots);
            Integer count = command.execute();
            assertAll(
                    () -> assertNotNull(count),
                    () -> assertEquals(expectedLargeSlots, count, () -> "Expected " + expectedLargeSlots + " but was " + count)
            );
            verify(parkingSlotDAO).countOfAvailable(SlotSize.LARGE);
        }
    }
}
