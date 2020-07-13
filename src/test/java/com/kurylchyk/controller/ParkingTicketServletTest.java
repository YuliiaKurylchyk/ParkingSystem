package com.kurylchyk.controller;

import com.kurylchyk.model.domain.customer.Customer;
import com.kurylchyk.model.domain.parkingSlots.ParkingSlot;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotStatus;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.Car;
import com.kurylchyk.model.domain.vehicles.Vehicle;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.CarSize;
import com.kurylchyk.model.exceptions.NoSuchParkingTicketException;
import com.kurylchyk.model.exceptions.ParkingSystemException;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.impl.parkingSlotDTO.ParkingSlotDTO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ParkingTicketServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    HttpSession session;

    @Mock
    ParkingTicketService parkingTicketService;

    @InjectMocks
    ParkingTicketServlet parkingTicketServlet;

    private static ParkingTicket parkingTicket;

    @BeforeAll
    public static void initAll() {

        Vehicle vehicle = new Car("Ford", "F-150", "AA 0011 OI", CarSize.PICK_UP);
        Customer customer = Customer.newCustomer().setCustomerID(1).setName("Name")
                .setSurname("Surname").setPhoneNumber("+380961025890").buildCustomer();
        ParkingSlot parkingSlot = new ParkingSlot(1, SlotSize.MEDIUM, SlotStatus.OCCUPIED);
        LocalDateTime arrivalTime = LocalDateTime.now().minusDays(5);
        Status status = Status.PRESENT;

        parkingTicket = ParkingTicket.newParkingTicket()
                .withParkingTicketID(1).withCustomer(customer).withVehicle(vehicle)
                .withParkingSlot(parkingSlot).withStatus(status).withArrivalTime(arrivalTime).buildTicket();
    }

    @BeforeEach
    public void init() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request,response);
        doNothing().when(response).sendRedirect(anyString());
        doNothing().when(session).setAttribute("currentTicket",parkingTicket);
        when(request.getSession()).thenReturn(session);
        doNothing().when(request).setAttribute(anyString(),anyString());

        when(request.getParameter("parkingTicketID")).thenReturn(String.valueOf(parkingTicket.getParkingTicketID()));
    }


    @Nested
    class GettingTicket {
        @Test
        @DisplayName("Should get ticket")
        public void shouldGetTicket() throws ParkingSystemException, ServletException, IOException {

            when(parkingTicketService.getByID(parkingTicket.getParkingTicketID())).thenReturn(parkingTicket);

            parkingTicketServlet.doGetTicket(request, response);

            verify(parkingTicketService).getByID(parkingTicket.getParkingTicketID());
            verify(session).setAttribute("currentTicket", parkingTicket);
        }


        @Test
        @DisplayName("Should NOT get parking ticket")
        public void shouldNotGetTicket() throws ParkingSystemException, ServletException, IOException {
            Integer wrongTicketID = 136;
            when(request.getParameter("parkingTicketID")).thenReturn(String.valueOf(wrongTicketID));
            when(parkingTicketService.getByID(wrongTicketID)).thenThrow(NoSuchParkingTicketException.class);
            parkingTicketServlet.doGetTicket(request, response);
            verify(request).setAttribute(eq("NotFound"), any(Exception.class));

        }

        @Test
        @DisplayName("Should get ticket by customer id")
        public void shouldByCustomerID() throws ServletException, IOException, ParkingSystemException {

            ArrayList<ParkingTicket> allParkingTickets = new ArrayList<>(Arrays.asList(parkingTicket));
            when(request.getParameter("customerID")).thenReturn(String.valueOf(parkingTicket.getCustomer().getCustomerID()));
            when(parkingTicketService.getByCustomer(parkingTicket.getCustomer().getCustomerID())).thenReturn(allParkingTickets);

            parkingTicketServlet.doShowByCustomer(request,response);

            verify(parkingTicketService).getByCustomer(parkingTicket.getCustomer().getCustomerID());
            verify(request).setAttribute("appropriateTickets", allParkingTickets);
        }


        @Test
        @DisplayName("Should get parking ticket by parking slot")
        public void shouldGetTicketBySlot() throws ParkingSystemException, ServletException, IOException {
            when( request.getParameter("parkingSlotID")).thenReturn(String.valueOf(parkingTicket.getParkingSlot().getParkingSlotID()));
            when(request.getParameter("slotSize")).thenReturn(String.valueOf(parkingTicket.getParkingSlot().getSizeOfSlot()));
            when(parkingTicketService.getByParkingSlot(any(ParkingSlotDTO.class))).thenReturn(parkingTicket);

            parkingTicketServlet.doShowBySlot(request,response);

            verify(parkingTicketService).getByParkingSlot((any(ParkingSlotDTO.class)));

        }

        @Test
        @DisplayName("Should get parking ticket by vehicle id")
        public void shouldGetTicketByVehicle() throws ParkingSystemException, ServletException, IOException {
            when( request.getParameter("vehicleID")).thenReturn(parkingTicket.getVehicle().getLicensePlate());

            when(parkingTicketService.getByVehicle(parkingTicket.getVehicle().getLicensePlate())).thenReturn(parkingTicket);

            parkingTicketServlet.doShowByVehicle(request,response);

            verify(parkingTicketService).getByVehicle(parkingTicket.getVehicle().getLicensePlate());

        }

    }
    @Test
    @DisplayName("Should create parking ticket")
    public void shouldCreateTicket() throws ParkingSystemException, ServletException, IOException {

        when(session.getAttribute("vehicle")).thenReturn(parkingTicket.getVehicle());
        when(session.getAttribute("parkingSlot")).thenReturn(parkingTicket.getParkingSlot());
        when(session.getAttribute("customer")).thenReturn(parkingTicket.getCustomer());

        when(parkingTicketService.createParkingTicket(parkingTicket.getVehicle(),parkingTicket.getCustomer(),
                parkingTicket.getParkingSlot())).thenReturn(parkingTicket);

        when(parkingTicketService.saveToDB(parkingTicket)).thenReturn(parkingTicket);

        doNothing().when(session).removeAttribute(anyString());

        parkingTicketServlet.doCreate(request,response);

        verify(parkingTicketService).createParkingTicket(parkingTicket.getVehicle(),parkingTicket.getCustomer(),parkingTicket.getParkingSlot());
        verify(parkingTicketService).saveToDB(parkingTicket);

        verify(session).removeAttribute("vehicle");
        verify(session).removeAttribute("customer");
        verify(session).removeAttribute("parkingSlot");

    }


    @Test
    @DisplayName("Should show parking ticket")
    public void shouldShowParkingTicket() throws ParkingSystemException, ServletException, IOException {

        when(request.getParameter("parkingTicketID")).thenReturn(String.valueOf(parkingTicket.getParkingTicketID()));

        when(parkingTicketService.getByID(parkingTicket.getParkingTicketID())).thenReturn(parkingTicket);

        parkingTicketServlet.doShow(request,response);
        verify(parkingTicketService).getByID(parkingTicket.getParkingTicketID());
        verify(session).setAttribute("currentTicket",parkingTicket);
    }


    @Test
    @DisplayName("Should remove parking ticket")
    public void shouldRemoveParkingTicket() throws ParkingSystemException, ServletException, IOException {

        ParkingTicket removedTicket = parkingTicket;
        removedTicket.setDepartureTime(LocalDateTime.now());
        removedTicket.setCost(new BigDecimal(45));

        when(request.getParameter("parkingTicketID")).thenReturn(String.valueOf(parkingTicket.getParkingTicketID()));

        when(parkingTicketService.getByID(parkingTicket.getParkingTicketID())).thenReturn(parkingTicket);
        when(parkingTicketService.remove(parkingTicket)).thenReturn(removedTicket);

        parkingTicketServlet.doRemove(request,response);

        verify(parkingTicketService).getByID(parkingTicket.getParkingTicketID());
        verify(parkingTicketService).remove(parkingTicket);

    }

    @Test
    @DisplayName("Should delete parking ticket")
    public void shouldDeleteParkingTicket() throws ParkingSystemException, ServletException, IOException {



        when(request.getParameter("parkingTicketID")).thenReturn(String.valueOf(parkingTicket.getParkingTicketID()));

        when(parkingTicketService.getByID(parkingTicket.getParkingTicketID())).thenReturn(parkingTicket);
        when(parkingTicketService.deleteCompletely(parkingTicket)).thenReturn(parkingTicket);

        parkingTicketServlet.doDeleteCompletely(request,response);

        verify(parkingTicketService).getByID(parkingTicket.getParkingTicketID());
        verify(parkingTicketService).deleteCompletely(parkingTicket);

    }


    @Nested
    class ShowAllTickets{


        @Test
        @DisplayName("Should show all tickets")
        public void shouldShowAllTicket() throws ParkingSystemException, ServletException, IOException {

            ArrayList<ParkingTicket> allTickets = new ArrayList<>(Arrays.asList(parkingTicket));

            when(request.getParameter("date")).thenReturn("allTickets");
            when(request.getParameter("status")).thenReturn("ALL");

            when(parkingTicketService.getAllTickets()).thenReturn(allTickets);

            parkingTicketServlet.doShowAll(request,response);

            verify(parkingTicketService).getAllTickets();
            verify(parkingTicketService,never()).getAllInDate(any(LocalDateTime.class));
            verify(parkingTicketService,never()).getAll(any(Status.class));
            verify(parkingTicketService,never()).getAll(any(Status.class));
            verify(parkingTicketService,never()).getAllInDateAndStatus(any(LocalDateTime.class),any(Status.class));
        }


        @Test
        @DisplayName("Should show all tickets in appropriate day")
        public void shouldShowAllTicketInDay() throws ParkingSystemException, ServletException, IOException {

            ArrayList<ParkingTicket> allTickets = new ArrayList<>(Arrays.asList(parkingTicket));

            when(request.getParameter("date")).thenReturn("today");
            when(request.getParameter("status")).thenReturn("ALL");

            when(parkingTicketService.getAllInDate(any(LocalDateTime.class))).thenReturn(allTickets);

            parkingTicketServlet.doShowAll(request,response);

            verify(parkingTicketService,never()).getAllTickets();
            verify(parkingTicketService).getAllInDate(any(LocalDateTime.class));
            verify(parkingTicketService,never()).getAll(any(Status.class));
            verify(parkingTicketService,never()).getAll(any(Status.class));
            verify(parkingTicketService,never()).getAllInDateAndStatus(any(LocalDateTime.class),any(Status.class));
        }


        @Test
        @DisplayName("Should show tickets  with appropriate status")
        public void shouldShowAllTicketsWithStatus() throws ParkingSystemException, ServletException, IOException {

            ArrayList<ParkingTicket> allTickets = new ArrayList<>(Arrays.asList(parkingTicket));

            Status expectedStatus = Status.PRESENT;
            when(request.getParameter("date")).thenReturn("allTickets");
            when(request.getParameter("status")).thenReturn(String.valueOf(expectedStatus));

            when(parkingTicketService.getAll(expectedStatus)).thenReturn(allTickets);
            parkingTicketServlet.doShowAll(request,response);

            verify(parkingTicketService,never()).getAllTickets();
            verify(parkingTicketService,never()).getAllInDate(any(LocalDateTime.class));
            verify(parkingTicketService).getAll(expectedStatus);
            verify(parkingTicketService,never()).getAllInDateAndStatus(any(LocalDateTime.class),any(Status.class));
        }

        @Test
        @DisplayName("Should show tickets  with appropriate status and date")
        public void shouldShowAllTicketsWithStatusAndDate() throws ParkingSystemException, ServletException, IOException {

            ArrayList<ParkingTicket> allTickets = new ArrayList<>(Arrays.asList(parkingTicket));

            Status expectedStatus = Status.PRESENT;
            when(request.getParameter("date")).thenReturn("yesterday");
            when(request.getParameter("status")).thenReturn(String.valueOf(expectedStatus));

            when(parkingTicketService.getAll(expectedStatus)).thenReturn(allTickets);
            parkingTicketServlet.doShowAll(request,response);

            verify(parkingTicketService,never()).getAllTickets();
            verify(parkingTicketService,never()).getAllInDate(any(LocalDateTime.class));
            verify(parkingTicketService,never()).getAll(expectedStatus);
            verify(parkingTicketService).getAllInDateAndStatus(any(LocalDateTime.class),eq(expectedStatus));
        }
    }

}
