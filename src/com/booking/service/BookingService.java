package com.booking.service;

import com.booking.dao.BookingDao;
import com.booking.dao.IBookingDao;
import com.booking.dao.FlightDao;
import com.booking.entities.Booking;
import com.booking.entities.Flight;
import com.booking.exception.NotFoundData;

import java.time.Instant;
import java.util.List;

public class BookingService {
    private IBookingDao<Booking, Long> bookingDao = BookingDao.getInstance();
    private FlightDao flightDao = FlightDao.getInstance();

    public void save(Booking booking) {
        bookingDao.save(booking);
    }

    public Booking create(Flight flight, int userId, List<Integer> users) throws NotFoundData {
        if (flight == null || flightDao.get(flight.getId()) == null) {
            throw new NotFoundData("You can't make a booking. There is no such flight!");
        }

        Booking newBooking = new Booking(Instant.now().toEpochMilli(), flight.getId(), userId, users);

        int curFreeSeats = flight.getSeatsFree();
        int bookSeats = users.size();

        flight.setSeatsFree(curFreeSeats - bookSeats);

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

    public Booking delete(Booking booking) throws NotFoundData {
        Flight flight = flightDao.get(booking.getFlightId());

        if (flight != null) {
            int curFreeSeats = flight.getSeatsFree();
            int bookSeats = booking.getUsers().size();

            flight.setSeatsFree(curFreeSeats + bookSeats);

            flightDao.update(flight);
        }

        Booking bookingDeleted = bookingDao.delete(booking.getId());

        if (bookingDeleted == null) {
            throw new NotFoundData("There is no such booking!");
        }

        return bookingDeleted;
    }
}
