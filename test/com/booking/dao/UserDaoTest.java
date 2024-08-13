package com.booking.dao;

import com.booking.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao =  UserDao.getInstance();
    }

    @Test
    public void testSaveUser() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", "password123");
        userDao.save(user);

        User result = userDao.get(1);
        assertEquals(user, result);
    }
}
