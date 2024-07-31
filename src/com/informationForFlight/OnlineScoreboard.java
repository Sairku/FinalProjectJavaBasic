package com.informationForFlight;

import java.io.Serializable;
import java.util.Random;

public class OnlineScoreboard implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String departureTime;
    private String from;
    private String to;
    private int freeSeats = 100;

    public OnlineScoreboard(String from, String to, String departureTime){
        this.id = new Random().nextInt(90000)+10000;
        this.from = from;
        this.to = to;
        this.departureTime = String.valueOf(departureTime);
    }

    @Override
    public String toString() {
        return "id = " + id + ", departureTime = " + departureTime + ", from = " + from + ", to = " + to + ", freeSeats = " + freeSeats;
    }
}
