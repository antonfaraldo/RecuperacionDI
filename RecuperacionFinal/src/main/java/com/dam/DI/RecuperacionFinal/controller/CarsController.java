package com.dam.DI.RecuperacionFinal.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dam.DI.RecuperacionFinal.model.Car;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;



public class CarsController {
    @FXML
    private TextField txtSearch; // Cuadro de busqueda
    @FXML
    private FlowPane carsContainer; // Contenedor de las tarjetas
    @FXML
    public void initialize() {
        // Lista de prueba
    	List<Car> mockCars = new ArrayList<>();
    	mockCars.add(new Car(1, "Toyota", "Yaris", 116, "Compact", LocalDateTime.now(), "", false));
    	mockCars.add(new Car(2, "Ford", "Mustang", 450, "Sports", LocalDateTime.now(), "", true));
    	mockCars.add(new Car(3, "Audi", "A4", 204, "Sedan", LocalDateTime.now(), "", false));
    	
    	// Se cargan e  inyectan en cada tarjeta
    	for (Car car : mockCars) {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/card_view.fxml"));
    			Parent cardNode = loader.load();
    			
    			// Se recupera el controlador de la tarjeta que asigna el coche
    			CardController cardController = loader.getController();
    			cardController.setCarData(car);
    			
    			// Se añade el nodo visual al Flowpane
    			carsContainer.getChildren().add(cardNode);
    		} catch (IOException e) {
				// TODO: handle exception
    			System.err.println("Error rendering car card: " + e.getMessage());
			}
    	}
    	
    }
}
