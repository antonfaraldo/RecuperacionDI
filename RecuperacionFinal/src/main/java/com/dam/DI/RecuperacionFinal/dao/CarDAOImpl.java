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
}
