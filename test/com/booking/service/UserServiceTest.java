package com.booking.service;

import com.booking.entities.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService = new UserService();

    @Test
    public void testGetUserByName() {
        User user = new User(2, "Johnn", "Doe", "john.doe@example.com", "password123");
        userService.save(user);

        User result = userService.getUserByName("Johnn","Doe");
        assertEquals(user, result);
    }

    @Test
    public void testGetUserByNameNotFindName() {
        User result = userService.getUserByName("Johnnn","Doe");
        assertNull(result);
    }
}