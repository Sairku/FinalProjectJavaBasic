package com.booking.controller;

import com.booking.entities.User;
import com.booking.service.UserService;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.SocketHandler;
import java.util.regex.Pattern;

public class UserController {
    private static final String FILE_PATH = "files/users.dat";
    private UserService userService = new UserService();

    public User chooseAuthenticationOption(Scanner scanner) {
        User user = null;
        String choice;

        do {
            System.out.println("Welcome! Please choose an option: ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    user = authenticateUser(scanner);
                    if (user != null)
                        return user;
                    break;
                case "2":
                    user = createUser(scanner);
                    if (user != null) {
                        System.out.println("Registration successful.");
                    } else {
                        System.out.println("Registration failed.");
                    }
                    break;
                case "3":
                    System.out.println("Exit");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }

    public User authenticateUser(Scanner scanner) {
        System.out.println("Please enter your login credentials.");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean isValid = validateCredentials(email, password);

        if (isValid) {
            // List<User> users = readUsersFromFile();
            User user = userService.getUserByLogin(email, password);

            if (user == null) {
                System.out.println("User not found or invalid credentials.");
            } else {
                System.out.println("Welcome, " + user.getName() + " " + user.getSurname() + "!");
            }

            return user;
        } else {
            System.out.println("Authentication failed.");
            return null;
        }
    }

    public boolean validateCredentials(String email, String password) {
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return false;
        }

        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }


        return true;
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$", Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    public void readUsersFromFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            List<User> users = (List<User>) ois.readObject();
            System.out.println(users);
            userService.fillUsers(users);

        } catch (FileNotFoundException e) {
            System.out.println("User file not found. A new one will be created.");
        } catch (EOFException e) { } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error reading user file: " + e.getMessage());
        }

    }

    public void saveUsersToFile() {
        List<User> users = userService.getUsers();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error writing to user file: " + e.getMessage());
        }
    }

    private int generateNewUserId() {
        List<User> users = userService.getUsers();
        return users.stream().mapToInt(User::getId).max().orElse(0) + 1;
    }

    public User createUser(Scanner scanner) {
        int id = generateNewUserId();

        System.out.print("Enter your first name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String surname = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return null;
        }

        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return null;
        }

//        User user = new User(id, name, surname, email, password);
//
//        userService.save(user);
//        return user;

        return userService.createUser(name, surname, email, password);
    }
}
