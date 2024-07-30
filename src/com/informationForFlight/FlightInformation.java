package com.informationForFlight;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

public class FlightInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private LocalDateTime departureTime;
    private String from;
    private String to;
    private int freeSeats = 50;

    public FlightInformation (String from, String to, LocalDateTime departureTime,int freeSeats ){
        this.id = new Random().nextInt(90000)+10000;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.freeSeats = freeSeats;
    }
}


