package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.exceptions.PasswordIncorrectException;
import model.exceptions.UserExistsException;
import model.exceptions.UserNotFoundException;

public class UserManager {
    private HashMap<String, User> users;
    private Connection db;

    public UserManager() {
        this(false);
    }

    public UserManager(boolean noSQL) {
        this.users = new HashMap<String, User>();

        if (!noSQL) {
            try {
                // Create the connection
                this.db = DriverManager.getConnection("jdbc:sqlite:./user.db");

                // Create the table if it doesn't exist
                try (Statement statement = this.db.createStatement()) {
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (username VARCHAR(100) PRIMARY KEY, password_hash VARCHAR(100), first_name VARCHAR(100), last_name VARCHAR(100), profile_picture VARCHAR(100), premium BOOLEAN)");
                }

                try (Statement statement = this.db.createStatement()) {
                    ResultSet userSet = statement.executeQuery("SELECT username, password_hash, first_name, last_name, profile_picture, premium FROM users");

                    while (userSet.next()) {
                        User user = new User(
                            userSet.getString("first_name"),
                            userSet.getString("last_name"),
                            userSet.getString("username"),
                            HashedPassword.fromHash(userSet.getString("password_hash")),
                            userSet.getString("profile_picture"),
                            userSet.getBoolean("premium")
                        );

                        this.users.put(user.getUsername(), user);
                    }
                }
            } catch (SQLException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("User loading SQL error");
                a.setContentText(e.getMessage());
                a.show();
            }
        }
    }

    public void addUser(User user) throws UserExistsException {
        if (this.users.containsKey(user.getUsername())) throw new UserExistsException(user.getUsername());

        if (this.db != null) {
            // Save to database
            try (PreparedStatement statement = this.db.prepareStatement("INSERT INTO users (username, password_hash, first_name, last_name, profile_picture, premium) VALUES (?, ?, ?, ?, ?, ?)")) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPasswordHash().getHash());
                statement.setString(3, user.getFirstName());
                statement.setString(4, user.getLastName());
                statement.setString(5, user.getProfilePhoto());
                statement.setBoolean(6, user.getPremium());

                statement.execute();
            } catch (SQLException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("User saving SQL error");
                a.setContentText(e.getMessage());
                a.show();
            }
        }

        this.users.put(user.getUsername(), user);
    }

    public void updateUser(User user) throws UserNotFoundException {
        if (this.users.containsKey(user.getUsername())) {
            this.users.put(user.getUsername(), user);

            if (this.db != null) {
                try (PreparedStatement statement = this.db.prepareStatement("UPDATE users SET (first_name, last_name, profile_picture, premium) = (?, ?, ?, ?) WHERE username = ?")) {
                    statement.setString(1, user.getFirstName());
                    statement.setString(2, user.getLastName());
                    statement.setString(3, user.getProfilePhoto());
                    statement.setBoolean(4, user.getPremium());
                    statement.setString(5, user.getUsername());

                    statement.execute();
                } catch (SQLException e) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setHeaderText("User saving SQL error");
                    a.setContentText(e.getMessage());
                    a.show();
                }
            }
        } else throw new UserNotFoundException(user.getUsername());
    }

    public User login(String username, String password) throws UserNotFoundException, PasswordIncorrectException {
        if (this.users.containsKey(username)) {
            User user = this.users.get(username);

            HashedPassword hashedPassword = new HashedPassword(password);

            if (user.getPasswordHash().getHash().equals(hashedPassword.getHash())) return user;
            else throw new PasswordIncorrectException(username);
        } else throw new UserNotFoundException(username);
    }

    public int getUserCount() {
        return this.users.size();
    }
}