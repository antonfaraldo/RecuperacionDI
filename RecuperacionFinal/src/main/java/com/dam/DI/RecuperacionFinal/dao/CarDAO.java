package com.dam.DI.RecuperacionFinal.dao;

import com.dam.DI.RecuperacionFinal.model.Car;

import java.util.List;

public interface CarDAO {
    List<Car> getAllCars(int currentUserId);
    boolean toggleFavorite(int userId, int carId, boolean favorite);
    boolean createCar(Car car);
}
