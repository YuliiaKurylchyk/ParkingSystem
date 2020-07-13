package com.kurylchyk.controller;

import com.kurylchyk.model.domain.parkingSlots.slotEnum.SlotSize;
import com.kurylchyk.model.domain.parkingTicket.ParkingTicket;
import com.kurylchyk.model.domain.parkingTicket.ticketEnum.Status;
import com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType;
import com.kurylchyk.model.services.ParkingSlotService;
import com.kurylchyk.model.services.ParkingTicketService;
import com.kurylchyk.model.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class HomePageServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    HttpSession session;

    @Mock
    VehicleService vehicleService;

    @Mock
    ParkingTicketService parkingTicketService;

    @Mock
    ParkingSlotService parkingSlotService;

    @InjectMocks
    HomePageServlet homePageServlet;


    @BeforeEach
    public void init() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request,response);
        doNothing().when(request).setAttribute(anyString(),anyObject());
        doNothing().when(response).sendRedirect(anyString());
        when(request.getSession()).thenReturn(session);

    }

    @Test
    @DisplayName("Should get vehicle info")
    public  void shouldGetVehicleInfo() throws Exception {
        Integer expectedNumberOfBikes = 7;
        Integer expectedNumberOfCars = 12;
        Integer expectedNumberOfTrucks = 2;
        Integer expectedNumberOfBuses = 9;

        when(vehicleService.countAllPresent(VehicleType.MOTORBIKE)).thenReturn(expectedNumberOfBikes);
        when(vehicleService.countAllPresent(VehicleType.CAR)).thenReturn(expectedNumberOfCars);
        when(vehicleService.countAllPresent(VehicleType.TRUCK)).thenReturn(expectedNumberOfTrucks);
        when(vehicleService.countAllPresent(VehicleType.BUS)).thenReturn(expectedNumberOfBuses);

        homePageServlet.getVehicleInfo(request);

        verify(request).setAttribute("numberOfBikes",expectedNumberOfBikes);
        verify(request).setAttribute("numberOfCars",expectedNumberOfCars);
        verify(request).setAttribute("numberOfTrucks",expectedNumberOfTrucks);
        verify(request).setAttribute("numberOfBuses",expectedNumberOfBuses);
    }


    @Test
    @DisplayName("Should get parking tickets info")
    public  void shouldGetParkingTicketsInfo() throws Exception {


        List<ParkingTicket> todayEntries = new ArrayList<>(Arrays.asList(ParkingTicket.newParkingTicket().buildTicket()));

        List<ParkingTicket> allPresent = new ArrayList<>(Arrays.asList(
                ParkingTicket.newParkingTicket().buildTicket(),
                ParkingTicket.newParkingTicket().buildTicket()));


        List<ParkingTicket> allLeft = new ArrayList<>(Arrays.asList(
                ParkingTicket.newParkingTicket().buildTicket(),
                ParkingTicket.newParkingTicket().buildTicket(),
                ParkingTicket.newParkingTicket().buildTicket()
                ));

        when(parkingTicketService.getAllInDate(any(LocalDateTime.class))).thenReturn(todayEntries);
        when(parkingTicketService.getAll(Status.PRESENT)).thenReturn(allPresent);
        when(parkingTicketService.getAll(Status.LEFT)).thenReturn(allLeft);

       doNothing().when(request).setAttribute(anyString(),anyInt());

        homePageServlet.getParkingTicketInfo(request);

        verify(request).setAttribute("countOfTodayEntities",todayEntries.size());
        verify(request).setAttribute("countOfAllLeft",allLeft.size());
        verify(request).setAttribute("countOfAllPresent",allPresent.size());

    }


    @Test
    @DisplayName("Should get parking slot info")
    public  void shouldGetParkingSlotInfo() throws Exception {

        Integer expectedCountOfSmallPresent = 14;
        Integer expectedCountOfMediumPresent = 20;
        Integer expectedCountOfLargePresent = 5;

        when(parkingSlotService.countAvailableSlot(SlotSize.SMALL)).thenReturn(expectedCountOfSmallPresent);
        when(parkingSlotService.countAvailableSlot(SlotSize.MEDIUM)).thenReturn(expectedCountOfMediumPresent);
        when(parkingSlotService.countAvailableSlot(SlotSize.LARGE)).thenReturn(expectedCountOfLargePresent);

        doNothing().when(request).setAttribute(anyString(),anyInt());

        homePageServlet.getParkingSlotInfo(request);

        verify(request).setAttribute("smallSlots",expectedCountOfSmallPresent);
        verify(request).setAttribute("mediumSlots",expectedCountOfMediumPresent);
        verify(request).setAttribute("largeSlots",expectedCountOfLargePresent);

    }


    @Test
    @DisplayName("Should set ukrainian locale")
    public void shouldSetUkrainianLocale(){
        when(session.isNew()).thenReturn(true);

        homePageServlet.setLanguage(request);
        verify(session).setAttribute("sessionLocale","uk");
        verify(session,never()).setAttribute("sessionLocale","en");
    }

    @Test
    @DisplayName("Should set english locale")
    public void shouldSetEnglishLocale(){
        when(session.isNew()).thenReturn(false);
        when(request.getParameter("language")).thenReturn("en");

        homePageServlet.setLanguage(request);
        verify(session,never()).setAttribute("sessionLocale","uk");
        verify(session).setAttribute("sessionLocale",request.getParameter("language"));
    }
}
