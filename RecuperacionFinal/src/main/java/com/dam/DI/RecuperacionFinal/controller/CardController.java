package com.dam.DI.RecuperacionFinal.controller;


import com.dam.DI.RecuperacionFinal.model.Car;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class CardController {
	@FXML private Label lblBrandModel; // Marca y Modelo
	@FXML private Label lblSpecs; // Especificaciones de potencia de motor y tipo de coche 
	@FXML private ToggleButton btnFavorite;
	
	public void setCarData(Car car) {
		lblBrandModel.setText(car.getBrand() + " " + car.getModel());
		lblSpecs.setText(car.getHorsePower() + " HP - " + car.getType());
		btnFavorite.setSelected(car.isFavorite());
	}
	

}
