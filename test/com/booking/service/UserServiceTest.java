package com.booking.service;

import com.booking.dao.UserDao;
import com.booking.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    public void testGetUserById() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", "password123");
        when(userDao.getUserById(1)).thenReturn(user);

        User result = userService.getUserById(1);
        assertEquals(1, result.getId());
        assertEquals("John", result.getName());
    }
}
