package com.dam.DI.RecuperacionFinal.controller;


import com.dam.DI.RecuperacionFinal.dao.CarDAO;
import com.dam.DI.RecuperacionFinal.dao.CarDAOImpl;
import com.dam.DI.RecuperacionFinal.model.Car;

import com.dam.DI.RecuperacionFinal.model.User;
import com.dam.DI.RecuperacionFinal.util.AppShell;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class CardController {
	@FXML private Label lblBrandModel; // Marca y Modelo
	@FXML private Label lblSpecs; // Especificaciones de potencia de motor y tipo de coche 
	@FXML private ToggleButton btnFavorite;

    private CarDAO carDAO = new CarDAOImpl();
    private Car currentCar;
	
	public void setCarData(Car car) {
		this.currentCar = car;
		lblBrandModel.setText(car.getBrand() + " " + car.getModel());
		lblSpecs.setText(car.getHorsePower() + " HP - " + car.getType());
		btnFavorite.setSelected(car.isFavorite());

        btnFavorite.setText(car.isFavorite() ? "❤ Favorited" : "🖤 Add Favorite");
	}

    @FXML
    public  void initialize() {
        btnFavorite.setOnAction(event -> {
            User currentUser = AppShell.getInstance().getSessionUser();

            if (currentUser != null && currentCar != null) {
                boolean newState = btnFavorite.isSelected();
                boolean success = carDAO.toggleFavorite(currentUser.getId(), currentCar.getId(), newState);

                if (success) {
                    currentCar.setFavorite(newState);
                    System.out.println("Coche ID " +  currentCar.getId() + " actualizado como favorito: " + newState);
                    btnFavorite.setText(newState ? "❤ Favorited" : "🖤 Add Favorite");
                } else {
                    btnFavorite.setSelected(!newState);
                }
            }
        });
    }

}
