package com.booking.controller;

import com.booking.entities.Booking;
import com.booking.entities.Flight;
import com.booking.entities.User;
import com.booking.exception.MenuException;
import com.booking.exception.NotFoundData;
import com.booking.service.BookingService;
import com.booking.service.FlightService;
import com.booking.service.UserService;
import com.booking.utils.Input;
import com.booking.utils.Menu;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingController {
    private String FILE_NAME = "files/bookings.ser";

    private FlightService flightService = new FlightService();
    private UserService userService = new UserService();
    private BookingService bookingService = new BookingService();
    private Scanner input = new Scanner(System.in);

    private Flight chooseFlight(String cityFrom, String cityTo, LocalDate date, int seatsCount) {
        List<Flight> flights = flightService.findFlightsByData(cityFrom, cityTo, date, seatsCount);

        if (flights.isEmpty()) {
            System.out.println("There are no flights!"); // System.out.println("Немає рейсів! ");

            return null;
        }

        List<String> flightsMenu = flights.stream().map(Flight::toString).toList();

        while (true) {
            try {
                System.out.println("-------------------");
                int menuItem = Menu.chooseNumericMenu(flightsMenu, true, "Choose a flight: \n");

                if (menuItem == 0) {
                    return null;
                }

                return flights.get(menuItem - 1);
            } catch (MenuException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Booking bookFlight(int userId) {
        System.out.print("Enter your departure city: "); // System.out.print("Введіть місто вильоту: ");
        String cityFrom = input.nextLine();

        System.out.print("Enter your arrival city: "); // System.out.print("Введіть місто призначення: ");
        String cityTo = input.nextLine();

        LocalDate date = Input.enterDate(
                "Enter flight date in the format dd/MM/yyyy: ", // "Введіть дату вильоту у форматі dd/MM/yyyy: "
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        int seatsCount = Input.enterIntNumber("Enter the amount of passengers: ", 1); // System.out.print("Введіть кількість осіб: ");

        Flight flight = chooseFlight(cityFrom, cityTo, date, seatsCount);

        if (flight == null) {
            return null;
        }

        List<Integer> users = new ArrayList<>();

        System.out.println("-------------------");
        System.out.println("Let's enter passengers data");

        for (int i = 1; i <= seatsCount; i++) {
            System.out.println("Passenger " + i);

            System.out.print("First name: ");
            String name = input.nextLine();

            System.out.print("Last name: ");
            String surname = input.nextLine();

            User user = userService.getUserByName(name, surname);

            if (user == null) {
                user = userService.createUser(name, surname);
            }

            users.add(user.getId());
        }

        try {
            System.out.println("Your booking was created");
            return bookingService.create(flight, userId, users);
        } catch (NotFoundData e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteBooking() {
        long id = Input.enterLongNumber("Enter booking number: ", 1);

        Booking booking = bookingService.getById(id);

        if (booking == null) {
            System.out.println("Booking " + id + " not found!");
        } else {
            try {
                bookingService.delete(booking);
                System.out.println("Booking " + id + " was deleted!");
            } catch (NotFoundData e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void findBookings() {
        System.out.print("Please enter first name: ");
        String name = input.nextLine();

        System.out.print("Please enter last name: ");
        String surname = input.nextLine();

        User user = userService.getUserByName(name.trim(), surname.trim());

        if (user == null) {
            System.out.println("There are no bookings!"); // System.out.println("There is no such user!");
            return;
        }

        List<Booking> bookings = bookingService.getBookingByUser(user.getId());

        if (bookings.isEmpty()) {
            System.out.println("There are no bookings!");
        } else {
            System.out.println("Your bookings:");

            for (Booking booking : bookings) {
                System.out.printf(
                        "Booking [%d]: %s \n", 
                        booking.getId(),
                        flightService.get(booking.getFlightId()).toString()
                );
            }
        }
    }

    public void readDataFromFile() {
        try (
                FileInputStream fis = new FileInputStream(FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            Booking booking;

            while (true) {
                try {
                    booking = (Booking) ois.readObject();

                    bookingService.save(booking);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveDataToFile() {
        try (
                FileOutputStream fos = new FileOutputStream(FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            List<Booking> bookings = bookingService.getAll();

            for (Booking booking : bookings) {
                oos.writeObject(booking);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
