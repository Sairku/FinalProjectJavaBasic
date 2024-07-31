package com.booking.dao;

import com.booking.entities.Booking;
import com.booking.exception.NotFoundData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDaoImpl implements BookingDao<Booking, Long> {
    private static BookingDao<Booking, Long> bookingDao = null;
    private Map<Long, Booking> bookings = new HashMap<>();

    private BookingDaoImpl() {}

    public static BookingDao<Booking, Long> getInstance() {
        if (bookingDao == null) {
            bookingDao = new BookingDaoImpl();
        }

        return bookingDao;
    }

    @Override
    public Booking get(Long id) {
        return bookings.get(id);
    }

    @Override
    public List<Booking> getAll() {
        return bookings.values().stream().toList();
    }

    @Override
    public void save(Booking booking) {
        bookings.put(booking.getId(), booking);
    }

    @Override
    public void update(Booking booking) throws NotFoundData {
        if (bookings.containsKey(booking.getId())) {
            bookings.put(booking.getId(), booking);
        } else {
            throw new NotFoundData("Booking " + booking.getId() + " not found!");
        }
    }

    @Override
    public Booking delete(Long id) {
        return bookings.remove(id);
    }
}
