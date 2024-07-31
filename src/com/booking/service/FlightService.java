package com.booking.service;

import com.booking.dao.FlightDao;
import com.booking.entities.Flight;

import java.time.LocalDate;
import java.util.List;

public class FlightService {
    private FlightDao flightDao = FlightDao.getInstance();

    public void save(Flight flight) {
        flightDao.save(flight);
    }

    public List<Flight> getAll() {
        return flightDao.getAll();
    }

    public Flight get(int id) {
        return flightDao.get(id);
    }

    public void update(Flight flight) {
        flightDao.update(flight);
    }

    public Flight delete(int id) {
        Flight flight = flightDao.delete(id);

        if (flight == null) {
            System.out.println("Flight with ID " + id + " not found.");
        }

        return  flight;
    }

    public void fillFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            flightDao.save(flight);
        }
    }

    public List<Flight> findFlightsByData(String cityFrom, String cityTo, LocalDate date, int seatsCount) {
        return flightDao.getAll()
                .stream()
                .filter(flight ->
                        flight.getDeparture().equals(cityFrom) &&
                        flight.getDestination().equals(cityTo) && // flight.getDateTime().toLocalDate().equals(date) &&
                        flight.getSeatsFree() >= seatsCount
                )
                .toList();
    }
}
