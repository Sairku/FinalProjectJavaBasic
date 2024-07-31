package com.authentication;

import com.authorization.User;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserAuthentication {

    private static final String FILE_PATH = Paths.get("Files", "users.dat").toString();

    public boolean chooseAuthenticationOption(Scanner scanner) {
        String choice;
        do {
            System.out.println("Welcome! Please choose an option: ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (authenticateUser(scanner)) {
                        return true;
                    } else {
                        System.out.println("Authentication failed.");
                    }
                    break;
                case "2":
                    User newUser = createUser(scanner);
                    if (newUser != null) {
                        addUser(newUser);
                        System.out.println("Registration successful.");
                    } else {
                        System.out.println("Registration failed.");
                    }
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return false;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("3"));

        System.out.println("You have exited the program.");
        return false;
    }

    public boolean authenticateUser(Scanner scanner) {
        System.out.println("Please enter your login credentials.");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        return validateCredentials(email, password);
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

        List<User> users = readUsersFromFile();

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Welcome, " + user.getName() + " " + user.getSurname() + "!");
                return true;
            }
        }

        System.out.println("User not found or invalid credentials.");
        return false;
    }

    public boolean isValidEmail(String email) {
        return Pattern.compile("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$", Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    public void addUser(User user) {
        List<User> users = readUsersFromFile();
        users.add(user);
        writeUsersToFile(users);
    }

    private List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("User file not found. A new one will be created.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return users;
    }

    private void writeUsersToFile(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error writing to user file: " + e.getMessage());
        }
    }
    private int generateNewUserId() {
        List<User> users = readUsersFromFile();
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

        return new User(id, name, surname, email, password);
    }
}
