package com.booking.dao;

import com.booking.exception.NotFoundData;

import java.util.List;

public interface BookingDao<T, K> {
    T get(K id);
    List<T> getAll();
    void save(T t);
    void update(T t) throws NotFoundData;
    T delete(K id);
}
