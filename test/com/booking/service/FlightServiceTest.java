package com.booking.service;

import com.booking.entities.Flight;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightServiceTest {

    private FlightService flightService = new FlightService();

    @Test
    public void testGetFlightById() {
        Flight flight = new Flight(5,"Kyiv","Rome","2024-09-13 11:50",200);
        flightService.save(flight);

        Flight result = flightService.get(5);
        assertEquals(flight, result);
    }
}
