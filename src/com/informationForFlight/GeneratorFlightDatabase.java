package com.informationForFlight;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;

public class GeneratorFlightDatabase {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void GeneratorFlight(){
    List<OnlineScoreboard> flights = new ArrayList<>();
    Random random = new Random();
    String[] DESTINATIONS = {"London", "Paris", "Berlin", "Rome", "New York", "Tokyo", "Kyiv"};
    for (int i = 0; i < 100; i++) {
        String destinationFrom = DESTINATIONS[random.nextInt(DESTINATIONS.length)];
        String destinationTo = DESTINATIONS[random.nextInt(DESTINATIONS.length)];
        String departureTime = LocalDateTime.now().plusHours(random.nextInt(48)).format(FORMATTER);
        flights.add(new OnlineScoreboard( destinationFrom, destinationTo, departureTime));
    }
        String filePath = Paths.get("files","flights.txt").toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, java.nio.charset.StandardCharsets.UTF_8))) {
            for (OnlineScoreboard flight : flights) {
                writer.write(flight.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void getCurrentFlights() {
        String filePath = Paths.get("files","flights.txt").toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime dateTimePlus24Hours = currentDateTime.plusHours(24);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, java.nio.charset.StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String dateTimeString = parts[1].split("=")[1].trim();
                LocalDateTime inputDateTime = LocalDateTime.parse(dateTimeString, formatter);

                if (parts.length == 5 && inputDateTime.isBefore(dateTimePlus24Hours)) {
                    String lineTxt = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
                    System.out.println(lineTxt);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void getFlightsById(String flightId) {
        String filePath = Paths.get("files","flights.txt").toString();
        List<String> flights = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, java.nio.charset.StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flights.add(line);
            }
            Optional<String> filteredFlight = flights.stream()
                    .filter(flight -> flight.contains("id = " + flightId + ","))
                    .findFirst();

            if (filteredFlight.isPresent()) {
                System.out.println(filteredFlight.get());
            } else {
                System.out.println("The flight with the ID was not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
