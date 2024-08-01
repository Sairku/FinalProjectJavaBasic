package com.booking.controller;

import com.booking.entities.Flight;
import com.booking.service.FlightService;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class FlightController {
    public static void run() {
        FlightService flightService = new FlightService();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntInput(scanner, "Выберите опцию: ");

            switch (choice) {
                case 1:
                    createFlight(scanner, flightService);
                    break;
                case 2:
                    getFlightById(scanner, flightService);
                    break;
                case 3:
                    updateFlight(scanner, flightService);
                    break;
                case 4:
                    deleteFlight(scanner, flightService);
                    break;
                case 5:
                    listAllFlights(flightService);
                    break;
                case 0:
                    running = false;
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Меню управления полетами ---");
        System.out.println("1. Добавить новый полет");
        System.out.println("2. Найти полет по ID");
        System.out.println("3. Обновить информацию о полете");
        System.out.println("4. Удалить полет");
        System.out.println("5. Показать все полеты");
        System.out.println("0. Выйти");
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Пожалуйста, введите число: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static void createFlight(Scanner scanner, FlightService flightService) {
        int id = getIntInput(scanner, "Введите ID полета: ");
        String departure = getStringInput(scanner, "Введите пункт отправления: ");
        String destination = getStringInput(scanner, "Введите пункт назначения: ");
        String departureTime = getStringInput(scanner, "Введите время отправления: ");

        Flight flight = new Flight(id, destination, departure, departureTime, 250);

        flightService.save(flight);
        System.out.println("Полет добавлен: " + flight);
    }

    private static void getFlightById(Scanner scanner, FlightService flightService) {
        int id = getIntInput(scanner, "Введите ID полета для поиска: ");

        Flight flight = flightService.get(id);
        if (flight != null) {
            System.out.println("Найден полет: " + flight);
        } else {
            System.out.println("Полет с ID " + id + " не найден.");
        }
    }

    private static void updateFlight(Scanner scanner, FlightService flightService) {
        int id = getIntInput(scanner, "Введите ID полета для обновления: ");

        Flight flight = flightService.get(id);

        if (flight != null) {
            String destination = getStringInput(scanner, "Введите новый пункт назначения: ");
            String departureTime = getStringInput(scanner, "Введите новое время отправления: ");
            flight.setDestination(destination);
            flight.setDepartureTime(departureTime);
            flightService.update(flight);
            System.out.println("Информация о полете обновлена: " + flight);
        } else {
            System.out.println("Полет с ID " + id + " не найден.");
        }
    }

    private static void deleteFlight(Scanner scanner, FlightService flightService) {
        int id = getIntInput(scanner, "Введите ID полета для удаления: ");
        Flight flight = flightService.delete(id);
        if (flight != null) {
            System.out.println("Полет удален: " + flight);
        } else {
            System.out.println("Полет с ID " + id + " не найден.");
        }
    }

    private static void listAllFlights(FlightService flightService) {
        List<Flight> flights = flightService.getAll();

        if (flights.isEmpty()) {
            System.out.println("Список полетов пуст.");
        } else {
            System.out.println("--- Все полеты ---");
            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }
    }

    // Метод для записи данных в файл
    public void saveDataToFile() {
        FlightService flightService = new FlightService();

        List<Flight> flights = flightService.getAll();

        try (FileOutputStream fileOutputStream = new FileOutputStream("files/flights.dat");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(flights);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Метод для загрузки данных из файла (опционально)
    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        FlightService flightService = new FlightService();

        File file = new File("files/flights.dat");

        if (!file.exists()) {
            return; // Если файла нет, загружать нечего
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            List<Flight> flights = (List<Flight>) objectInputStream.readObject();

            flightService.fillFlights(flights);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
    }
}
