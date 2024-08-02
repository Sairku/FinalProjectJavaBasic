package com.booking.service;

import com.booking.dao.FlightDao;
import com.booking.entities.Booking;
import com.booking.entities.Flight;
import com.booking.exception.NotFoundData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @InjectMocks
    private final BookingService bookingService = new BookingService();

    @Mock
    private final FlightDao flightDao = mock(FlightDao.class);

    private Booking bookingNew;
    private final int flightId = 1;
    private final int userId = 1;

    @BeforeEach
    public void setUp() {
        long id = Instant.now().toEpochMilli();
        bookingNew = new Booking(id, flightId, userId, new ArrayList<>());
    }

    @Test
    public void testSave() {
        long id = bookingNew.getId();

        bookingService.save(bookingNew);

        assertNotNull(bookingService.getById(id));
        assertNull(bookingService.getById(Instant.now().toEpochMilli() + 20));
    }

    @Test
    public void testCreate() {
        Flight flight = new Flight(flightId, "Kyiv", "Lviv", "25/11/2024", 250);
        int userId = 11;
        List<Integer> users = new ArrayList<>();

        Booking booking = assertDoesNotThrow(() -> {
            when(flightDao.get(flight.getId())).thenReturn(flight);

            return bookingService.create(flight, userId, users);
        });

        assertNotNull(booking);
        assertThrows(NotFoundData.class, () -> bookingService.create(null, userId, users));
    }

    @Test
    public void testGetAll() {
        int expected = bookingService.getAll().size() + 1;

        bookingService.save(bookingNew);

        int actual = bookingService.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        bookingService.save(bookingNew);
        Booking actual = bookingService.getById(bookingNew.getId());

        assertEquals(bookingNew, actual);
    }

    @Test
    public void testGetBookingByUser() {
        int userId = 111;
        bookingNew.setUserId(userId);

        bookingService.save(bookingNew);
        List<Booking> bookings = bookingService.getBookingByUser(userId);

        assertEquals(1, bookings.size());
    }

    @Test
    public void testDeleteBooking() {
        int expectedSize = bookingService.getAll().size();

        bookingService.save(bookingNew);

        Booking booking = assertDoesNotThrow(() -> bookingService.delete(bookingNew));
        int actualSize = bookingService.getAll().size();

        assertEquals(bookingNew, booking);
        assertEquals(expectedSize, actualSize);

        assertThrows(NotFoundData.class, () -> bookingService.delete(bookingNew));
    }
}
