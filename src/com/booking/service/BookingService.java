package com.booking.service;

import com.booking.dao.BookingDaoImpl;
import com.booking.dao.BookingDao;
import com.booking.dao.FlightDaoImpl;
import com.booking.entities.Booking;
import com.booking.entities.Flight;
import com.booking.exception.NotFoundData;

import java.time.Instant;
import java.util.List;

public class BookingService {
    private BookingDao<Booking, Long> bookingDao = BookingDaoImpl.getInstance();
    private FlightDaoImpl flightDao = FlightDaoImpl.getInstance();

    public void save(Booking booking) {
        bookingDao.save(booking);
    }

    public Booking create(Flight flight, int userId, List<Integer> users) throws NotFoundData {
        Booking newBooking = new Booking(Instant.now().toEpochMilli(), flight.getId(), userId, users);

        int curFreeSeats = flight.getFreeSeatsCount();
        int bookSeats = users.size();

        flight.setFreeSeatsCount(curFreeSeats - bookSeats);

        flightDao.update(flight);
        bookingDao.save(newBooking);

        return newBooking;
    }

    public List<Booking> getAll() {
        return bookingDao.getAll();
    }

    public Booking getById(long id) {
        return bookingDao.get(id);
    }

    public List<Booking> getBookingByUser(int userId) {
        return bookingDao.getAll()
                .stream()
                .filter(booking -> {
                    List<Integer> users = booking.getUsers();
                    boolean isAmongUsers = false;

                    for (long id : users) {
                        if (id == userId) {
                            isAmongUsers = true;
                            break;
                        }
                    }

                    return booking.getUserId() == userId || isAmongUsers;
                })
                .toList();
    }

    public void deleteBooking(Booking booking) throws NotFoundData {
        Flight flight = flightDao.get(booking.getFlightId());
        int curFreeSeats = flight.getFreeSeatsCount();
        int bookSeats = booking.getUsers().size();

        flight.setFreeSeatsCount(curFreeSeats + bookSeats);

        flightDao.update(flight);
        bookingDao.delete(booking.getId());
    }
}
