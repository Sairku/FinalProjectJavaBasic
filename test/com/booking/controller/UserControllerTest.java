package com.booking.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController userController = new UserController();

    @Test
    public void testInvalidateEmail() {
        String email = "john.doeexample.com";
        String password = "12345678";

        assertFalse(userController.validateCredentials(email, password));
    }

    @Test
    public void testInvalidatePassword() {
        String email = "john.doee@xample.com";
        String password = "12345";

        assertFalse(userController.validateCredentials(email, password));
    }

    @Test
    public void testValidateCredentials() {
        String email = "john.doee@xample.com";
        String password = "12345678";

        assertTrue(userController.validateCredentials(email, password));
    }
}
