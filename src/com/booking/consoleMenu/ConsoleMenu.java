package com.booking.consoleMenu;

import com.booking.controller.BookingController;
import com.booking.controller.FlightController;
import com.booking.controller.UserController;
import com.booking.entities.User;
import com.booking.exception.MainMenuExeption;
import com.booking.informationForFlight.GeneratorFlightDatabase;

import java.util.Scanner;

public class ConsoleMenu {
    private UserController userController = new UserController();
    private BookingController bookingController = new BookingController();
    private FlightController flightController = new FlightController();

    private User currentUser = null;

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);

        currentUser = userController.chooseAuthenticationOption(scanner);

        if (currentUser == null) {
            System.out.println("Exiting...");
            return;
        }

        mainMenu(scanner);

        System.out.println("You have exited the program.");
    }

    private void mainMenu(Scanner scanner) {
        String command;
        String flightId;
        GeneratorFlightDatabase listTablo = new GeneratorFlightDatabase();
        listTablo.GeneratorFlight();

        userController.readUsersFromFile();
        flightController.loadDataFromFile();
        bookingController.readDataFromFile();

        do {
            displayMenu();
            System.out.println("Choose the option you want:");
            command = scanner.nextLine();
            try {
                switch (command.trim().toLowerCase()) {
                    case "1":
                        System.out.println("Online");
                        listTablo.getCurrentFlights();
                        break;
                    case "2":
                        System.out.print("Enter flight ID: ");
                        flightId = scanner.nextLine();
                        listTablo.getFlightsById(flightId);
                        break;
                    case "3":
                        bookingController.bookFlight(currentUser.getId());
                        break;
                    case "4":
                        bookingController.deleteBooking();
                        break;
                    case "5":
                        bookingController.findBookings();
                        break;
                    case "6":
                        System.out.println("Logging out...");
                        command = "exit";
                        userController.saveUsersToFile();
                        flightController.saveDataToFile();
                        bookingController.saveDataToFile();
                        break;
                    case "7":
                        System.out.println("Exiting...");
                        command = "exit";
                        userController.saveUsersToFile();
                        flightController.saveDataToFile();
                        bookingController.saveDataToFile();
                        break;
                    default:
                        throw new MainMenuExeption("Invalid command: " + command + ". Please try again.");
                }

            } catch (MainMenuExeption e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (!command.trim().toLowerCase().equals("exit"));

        System.out.println("You have logged out of the program");
    }

    private void displayMenu() {
        System.out.println("\n Main menu");
        System.out.println("1. Online scoreboard");
        System.out.println("2. View flight information");
        System.out.println("3. Search and book a flight");
        System.out.println("4. Cancel your reservation");
        System.out.println("5. My flights");
        System.out.println("6. Choose to log out");
        System.out.println("7. Choose to quit");
    }
}
