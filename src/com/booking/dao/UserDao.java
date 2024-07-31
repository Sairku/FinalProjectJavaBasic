package com.booking.dao;

import com.booking.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao implements IDao<User> {
    private static UserDao userDao = null;
    private List<User> users = new ArrayList<>();

    private UserDao() {}

    public static UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDao();
        }

        return userDao;
    }

    @Override
    public User get(int id) {
        User userRes = null;

        for (User user : users) {
            if (user.getId() == id) {
                userRes = user;
                break;
            }
        }

        if (userRes == null) {
            System.out.println("Not found user");
        }

        return userRes;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
        int index = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                index = i;
            }
        }

        if (index == -1) {
            users.add(user);
        } else {
            users.add(index, user);
        }
    }

    @Override
    public User delete(int id) {
        User user = null;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                user = users.get(i);
            }
        }

        if (user != null) {
            users.remove(user);
        }

        return user;
    }
}
