package com;

import com.utils.GeneratorFlightDatabase;

import java.util.Scanner;

public class ConsoleMenu {

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        String command;
        String flightId;
        GeneratorFlightDatabase listTablo = new GeneratorFlightDatabase();
        listTablo.GeneratorFlight();

        do {
            displayMenu();
            System.out.println("Choose the option you want:");
            command = scanner.nextLine();
            try{
                switch (command.trim().toLowerCase()){
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
                        System.out.println("flight");
                        break;
                    case "4":
                        System.out.println("reservation");
                        break;
                    case "5":
                        System.out.println("My flights");
                        break;
                    case "exit":
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid command. Please try again.");
                }

            } catch (MainMenuExeption e){
                System.out.println("Error: " + e.getMessage());
            }

        } while (!command.trim().toLowerCase().equals("exit"));

        System.out.println("You have logged out of the program");
    }

    private void displayMenu(){
        System.out.println("\n Main menu");
        System.out.println("1. Online scoreboard");
        System.out.println("2. View flight information");
        System.out.println("3. Search and book a flight");
        System.out.println("4. Cancel your reservation");
        System.out.println("5. My flights");
        System.out.println("6. Type 'exit' to quit");
    }

}
