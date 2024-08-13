package com.booking.controller;

import com.booking.entities.Flight;
import com.booking.service.FlightService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightControllerTest {

    private FlightController flightController = new FlightController();
    private FlightService flightService = new FlightService();
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // reassign the standard output stream to a new PrintStream with a ByteArrayOutputStream
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void setDown() {
        // reassign the standard output back
        System.setOut(standardOut);
    }

    @Test
    public void getFlightById() {
        Flight flight = new Flight(3,"Kyiv","Rome","2024-09-13 11:50",200);
        flightService.save(flight);

        String expectedPart = "найден полет";

        flightController.getFlightById("3");
        assertTrue(outputStreamCaptor.toString().trim().toLowerCase().contains(expectedPart));
    }
}
