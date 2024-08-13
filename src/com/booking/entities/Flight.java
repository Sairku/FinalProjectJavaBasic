package com.booking.entities;

import java.io.Serializable;

public class Flight implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String destination;
    private String departure;
    private String departureTime;
    private int seatsFree;

    public Flight(int id, String destination, String departure, String departureTime, int seatsFree) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.seatsFree = seatsFree;
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

    public int getSeatsFree() {
        return seatsFree;
    }

    public void setSeatsFree(int seatsFree) {
        this.seatsFree = seatsFree;
    }

    @Override
    public String toString() {
        return "Flight{id=" + id + ", destination='" + destination + "', departureTime='" + departureTime + "', freeSeats='" + seatsFree + "'}";
    }
}
