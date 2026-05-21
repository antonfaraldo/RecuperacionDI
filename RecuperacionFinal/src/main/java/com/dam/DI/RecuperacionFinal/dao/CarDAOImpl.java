package com.dam.DI.RecuperacionFinal.dao;

import com.dam.DI.RecuperacionFinal.model.Car;
import com.dam.DI.RecuperacionFinal.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {

    @Override
    public List<Car> getAllCars(int currentUserId) {
        List<Car> carList = new ArrayList<>();

        String query = "SELECT c.*, (f.car_id IS NOT NULL) AS marked_favorite " +
                        "FROM cars c " +
                        "LEFT JOIN user_favorites f ON c.id = f.car_id AND f.user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, currentUserId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Car car = new Car();
                    car.setId(rs.getInt("id"));
                    car.setBrand(rs.getString("brand"));
                    car.setModel(rs.getString("model"));
                    car.setHorsePower(rs.getInt("horse_power"));
                    car.setType(rs.getString("type"));
                    Timestamp sqlTimestamp = rs.getTimestamp("registration_date");

                    if (sqlTimestamp != null) {
                        car.setRegistrationDate(sqlTimestamp.toLocalDateTime());
                    } else {
                        car.setRegistrationDate(LocalDateTime.now());
                    }
                    car.setImageUrl(rs.getString("image_url"));
                    car.setFavorite(rs.getBoolean("marked_favorite"));

                    carList.add(car);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en getAllCars" + e.getMessage());
        }
        return carList;
    }

    @Override
    public boolean toggleFavorite(int userId, int carId, boolean isFavorite) {
        String query = isFavorite
                ? "INSERT INTO user_favorites (user_id, car_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE car_id = ?"
                : "DELETE FROM user_favorites WHERE user_id = ? AND car_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, carId);

            if (isFavorite) {
                preparedStatement.setInt(3, carId);
            }
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL en toggleFavorite" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean createCar(Car car) {
        String query = "INSERT INTO cars (brand, model, horse_power, type, image_url) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getHorsePower());
            stmt.setString(4, car.getType());
            stmt.setString(5, car.getImageUrl());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error in createCar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteCar(int carId) {
        String query = "DELETE FROM cars WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error in deleteCar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateCar(int carId, String brand, String model, int horsePower, String type, String imageUrl) {
        String query = "UPDATE cars SET brand = ?, model = ?, horse_power = ?, type = ?, image_url = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, brand);
            stmt.setString(2, model);
            stmt.setInt(3, horsePower);
            stmt.setString(4, type);
            stmt.setString(5, imageUrl);
            stmt.setInt(6, carId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error in updateCar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Integer getMostPopularCarId() {
        String query = "SELECT car_id, COUNT(*) AS total FROM user_favorites GROUP BY car_id ORDER BY total DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("car_id");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getMostPopularCarId: " + e.getMessage());
        }
        return null;
    }
}
