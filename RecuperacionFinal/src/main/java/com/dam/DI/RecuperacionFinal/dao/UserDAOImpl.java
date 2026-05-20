package com.dam.DI.RecuperacionFinal.dao;

import com.dam.DI.RecuperacionFinal.model.User;
import com.dam.DI.RecuperacionFinal.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // Provisionalmente se guarda en texto plano

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // El error 1062 es la restriccion de Unique duplicados en mysql
                System.err.println("El nombre de usuario ya existe");
            } else {
                System.err.println("Error SQL en registerUser: " + e.getMessage());
            }
            return false;
        }
    }

    @Override
    public User loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User loggedUser = new User();
                    loggedUser.setUsername(rs.getString("username"));
                    loggedUser.setEmail(rs.getString("email"));
                    loggedUser.setRole(rs.getString("role"));
                    return loggedUser;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en loginUser: " + e.getMessage());
        }
        return null;
    }
}
