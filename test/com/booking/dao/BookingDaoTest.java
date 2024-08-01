package com.booking.dao;

import com.booking.entities.Booking;
import com.booking.exception.NotFoundData;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDaoTest {
    private IBookingDao<Booking, Long> bookingDao = BookingDao.getInstance();

    @Test
    public void testSave() {
        long id = Instant.now().toEpochMilli();
        Booking booking = new Booking(id, 1, 1, new ArrayList<>());

        bookingDao.save(booking);

        Booking bookingSaved = bookingDao.get(id);

        assertEquals(booking, bookingSaved);
    }

    @Test
    public void testGet() {
        long id = Instant.now().toEpochMilli();
        Booking bookingNew = new Booking(id, 1, 1, new ArrayList<>());

        bookingDao.save(bookingNew);

        assertNotNull(bookingDao.get(id));
        assertNull(bookingDao.get(Instant.now().toEpochMilli()));
    }

    @Test
    public void testGetAll() {
        List<Booking> expected = new ArrayList<>(bookingDao.getAll());

        long id = Instant.now().toEpochMilli();
        Booking bookingNew = new Booking(id, 1, 1, new ArrayList<>());

        expected.add(bookingNew);
        expected.sort(Comparator.comparingLong(Booking::getId));

        bookingDao.save(bookingNew);
        List<Booking> actual = new ArrayList<>(bookingDao.getAll());
        actual.sort(Comparator.comparingLong(Booking::getId));

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        long id = Instant.now().toEpochMilli();
        Booking expected = new Booking(id, 1, 1, new ArrayList<>());

        bookingDao.save(expected);
        expected.setUserId(2);

        assertDoesNotThrow(() -> {
            bookingDao.update(expected);
        });

        Booking actual = bookingDao.get(id);
        assertEquals(expected, actual);

        assertThrows(NotFoundData.class, () -> {
            Booking bookingNew = new Booking(Instant.now().toEpochMilli(), 1, 1, new ArrayList<>());

            bookingDao.update(bookingNew);
        });
    }

    @Test
    public void testDelete() {
        long id = Instant.now().toEpochMilli();
        Booking booking = new Booking(id, 1, 1, new ArrayList<>());

        bookingDao.save(booking);
        int expected = bookingDao.getAll().size() - 1;

        bookingDao.delete(id);
        int actual = bookingDao.getAll().size();

        assertEquals(expected, actual);
    }
}
