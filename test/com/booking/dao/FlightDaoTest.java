package com.booking.dao;

import com.booking.entities.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FlightDaoTest {

    private FlightDao flightDao;

    @BeforeEach
    public void setUp() {
        flightDao =  flightDao.getInstance();
    }

    @Test
    public void testSave() {
        Flight flight = new Flight(1,"Kyiv","Rome","2024-09-13 11:50",200);
        flightDao.save(flight);

        Flight result = flightDao.get(1);
        assertEquals(flight, result);
    }

    @Test
    public void testNotFoundFlightId() {

        Flight result = flightDao.get(1111);
        assertNull(result);
    }
}
