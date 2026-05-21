package com.dam.DI.RecuperacionFinal.dao;

import com.dam.DI.RecuperacionFinal.model.Car;

import java.util.List;

public interface CarDAO {
    List<Car> getAllCars(int currentUserId);
    boolean toggleFavorite(int userId, int carId, boolean favorite);
    boolean createCar(Car car);
    boolean deleteCar(int carId);
    boolean updateCar(int carId, String brand, String model, int horsePower, String type, String imageUrl);
    Integer getMostPopularCarId();
}
