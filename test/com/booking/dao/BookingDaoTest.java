package com.booking.dao;

import com.booking.entities.Booking;
import com.booking.exception.NotFoundData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDaoTest {
    private final IBookingDao<Booking, Long> bookingDao = BookingDao.getInstance();

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
        bookingDao.save(bookingNew);

        Booking booking = bookingDao.get(bookingNew.getId());

        assertEquals(bookingNew, booking);
    }

    @Test
    public void testGet() {
        bookingDao.save(bookingNew);

        // check if new booking exists
        assertNotNull(bookingDao.get(bookingNew.getId()));

        // no booking
        assertNull(bookingDao.get(Instant.now().toEpochMilli() + 20));
    }

    @Test
    public void testGetAll() {
        List<Booking> expected = new ArrayList<>(bookingDao.getAll());

        expected.add(bookingNew);
        // sort for assertEquals, because na order may be different
        expected.sort(Comparator.comparingLong(Booking::getId));

        bookingDao.save(bookingNew);
        List<Booking> actual = new ArrayList<>(bookingDao.getAll());
        actual.sort(Comparator.comparingLong(Booking::getId)); // the same

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        Booking expected = bookingNew;

        bookingDao.save(expected);
        expected.setUserId(2);

        // check and update an existing booking
        assertDoesNotThrow(() -> {
            bookingDao.update(expected);
        });

        Booking actual = bookingDao.get(expected.getId());
        assertEquals(expected, actual);


        // check updating of non-existing booking
        assertThrows(NotFoundData.class, () -> {
            Booking bookingNew = new Booking(Instant.now().toEpochMilli() + 20, flightId, userId, new ArrayList<>());

            bookingDao.update(bookingNew);
        });
    }

    @Test
    public void testDelete() {
        bookingDao.save(bookingNew);
        int expected = bookingDao.getAll().size() - 1;

        bookingDao.delete(bookingNew.getId());
        int actual = bookingDao.getAll().size();

        assertEquals(expected, actual);
    }
}
