package com.treksathi.managers;

import com.treksathi.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String USER_FILE = "users.txt";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];
                    users.add(new User(username, password, role));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static User authenticateUser(String username, String password) {
        for (User user : loadUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
