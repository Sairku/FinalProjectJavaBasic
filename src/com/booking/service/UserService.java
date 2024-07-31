package com.booking.service;

import com.booking.dao.UserDao;
import com.booking.entities.User;

import java.util.List;

public class UserService {
    private UserDao userDao = UserDao.getInstance();

    public User getUserByLogin(String email, String password) {
        List<User> users = userDao.getAll();
        User userRes = null;

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                userRes = user;
                break;
            }
        }

        return userRes;
    }

    public User getUserByName(String name, String surname) {
        List<User> users = userDao.getAll();
        User userRes = null;

        for (User user : users) {
            if (user.getName().equals(name) && user.getSurname().equals(surname)) {
                userRes = user;
                break;
            }
        }

        return userRes;
    }

    public void fillUsers(List<User> users) {
        users.forEach(user -> userDao.save(user));
    }

    public List<User> getUsers() {
        return userDao.getAll();
    }

    public void save(User user) {
        userDao.save(user);
    }
}
