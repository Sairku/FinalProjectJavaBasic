package Flight.service;

import java.io.Serializable;

public class Flight implements Serializable {
    private final int id;
    private String destination;
    private String departure;
    private String departureTime;

    public Flight(int id, String destination, String departure, String departureTime) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    public int getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Flight{id=" + id + ", destination='" + destination + "', departureTime='" + departureTime + "'}";
    }
}
