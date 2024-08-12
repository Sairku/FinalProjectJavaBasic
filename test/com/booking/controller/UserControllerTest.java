package com.booking.controller;

import com.booking.entities.User;
import com.booking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void testGetUser() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", "password123");
        when(userService.getUserById(1)).thenReturn(user);

        User result = userController.getUser(1);
        assertEquals(1, result.getId());
        assertEquals("John", result.getName());
    }
}
