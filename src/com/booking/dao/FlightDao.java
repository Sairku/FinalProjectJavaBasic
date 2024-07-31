package com.booking.dao;

import com.booking.entities.Flight;
import com.booking.entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDao implements IDao<Flight> {
    private static FlightDao flightDao = null;
    private List<Flight> flights = new ArrayList<>();

    private FlightDao() {}

    public static FlightDao getInstance(){
        if (flightDao == null) {
            flightDao = new FlightDao();
        }

        return flightDao;
    }

    @Override
    public Flight get(int id) {
        // Поиск полета по ID
        for (Flight flight : flights) {
            if (flight.getId() == id) {
                return flight;
            }
        }
        return null; // Возвращаем null, если полет не найден
    }

    @Override
    public List<Flight> getAll() {
        // Возвращаем список всех полетов
        return flights;
    }

    @Override
    public void save(Flight flight) {
        // Проверяем, существует ли уже полет с таким же ID
        if (get(flight.getId()) != null) {
            System.out.println("Flight with ID " + flight.getId() + " already exists.");
            return;
        }

        // Добавляем полет в список
        flights.add(flight);
    }

    @Override
    public void update(Flight flight) {
        // Обновление полета в списке
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getId() == flight.getId()) {
                flights.set(i, flight);
                return;
            }
        }
        System.out.println("Flight with ID " + flight.getId() + " not found.");
    }

    @Override
    public Flight delete(int id) {
        // Удаление полета из списка
        Flight toRemove = null;

        for (Flight flight : flights) {
            if (flight.getId() == id) {
                toRemove = flight;
                break;
            }
        }

        if (toRemove != null) {
            flights.remove(toRemove);
            return toRemove;
        }
        return null;
    }
}
