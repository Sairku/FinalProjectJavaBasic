package com.booking.dao;

import com.booking.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = new UserDao();
        // Можна підключити до тестової бази даних або використовувати in-memory базу
    }

    @Test
    public void testAddUser() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", "password123");
        userDao.addUser(user);

        User result = userDao.getUserById(1);
        assertEquals("John", result.getName());
    }
}
