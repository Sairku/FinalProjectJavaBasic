package com.booking.entities;

import java.io.Serializable;
import java.util.List;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private int flightId;
    private long userId; // who booked
    private List<Integer> users;

    public Booking(long id, int flightId, long userId, List<Integer> users) {
        this.id = id;
        this.flightId = flightId;
        this.userId = userId;
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }
}
