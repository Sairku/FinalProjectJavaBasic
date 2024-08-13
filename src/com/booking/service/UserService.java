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
            if (user.getEmail() != null && user.getEmail().equals(email) && user.getPassword() != null && user.getPassword().equals(password)) {
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
    private int generateNewUserId() {

        UserService userService = new UserService();
        List<User> users = userService.getUsers();
        return users.stream().mapToInt(User::getId).max().orElse(0) + 1;
    }

    public User createUser(String name, String surname, String email, String password) {
        int id = generateNewUserId();

        User user = new User(id, name, surname, email, password);

        userDao.save(user);
        return user;
    }

    public User createUser(String name, String surname) {
        int id = generateNewUserId();

        User user = new User(id, name, surname);

        userDao.save(user);
        return user;
    }
}
