package Flight.controller;

import Flight.Dao.Dao;
import Flight.service.Flight;

import java.util.List;
import java.util.Scanner;

public class FlightConsoleController {

    public static void run() {
        Dao flightDao = new Dao();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntInput(scanner, "Выберите опцию: ");

            switch (choice) {
                case 1:
                    createFlight(scanner, flightDao);
                    break;
                case 2:
                    getFlightById(scanner, flightDao);
                    break;
                case 3:
                    updateFlight(scanner, flightDao);
                    break;
                case 4:
                    deleteFlight(scanner, flightDao);
                    break;
                case 5:
                    listAllFlights(flightDao);
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

    private static void createFlight(Scanner scanner, Dao flightDao) {
        int id = getIntInput(scanner, "Введите ID полета: ");
        String departure = getStringInput(scanner, "Введите пункт отправления: ");
        String destination = getStringInput(scanner, "Введите пункт назначения: ");
        String departureTime = getStringInput(scanner, "Введите время отправления: ");
        Flight flight = new Flight(id, destination, departure, departureTime);
        flightDao.save(flight);
        System.out.println("Полет добавлен: " + flight);
    }

    private static void getFlightById(Scanner scanner, Dao flightDao) {
        int id = getIntInput(scanner, "Введите ID полета для поиска: ");
        Flight flight = flightDao.get(id);
        if (flight != null) {
            System.out.println("Найден полет: " + flight);
        } else {
            System.out.println("Полет с ID " + id + " не найден.");
        }
    }

    private static void updateFlight(Scanner scanner, Dao flightDao) {
        int id = getIntInput(scanner, "Введите ID полета для обновления: ");
        Flight flight = flightDao.get(id);
        if (flight != null) {
            String destination = getStringInput(scanner, "Введите новый пункт назначения: ");
            String departureTime = getStringInput(scanner, "Введите новое время отправления: ");
            flight.setDestination(destination);
            flight.setDepartureTime(departureTime);
            flightDao.update(flight);
            System.out.println("Информация о полете обновлена: " + flight);
        } else {
            System.out.println("Полет с ID " + id + " не найден.");
        }
    }

    private static void deleteFlight(Scanner scanner, Dao flightDao) {
        int id = getIntInput(scanner, "Введите ID полета для удаления: ");
        Flight flight = flightDao.delete(id);
        if (flight != null) {
            System.out.println("Полет удален: " + flight);
        } else {
            System.out.println("Полет с ID " + id + " не найден.");
        }
    }

    private static void listAllFlights(Dao flightDao) {
        List<Flight> flights = flightDao.getAll();
        if (flights.isEmpty()) {
            System.out.println("Список полетов пуст.");
        } else {
            System.out.println("--- Все полеты ---");
            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }
    }
}
