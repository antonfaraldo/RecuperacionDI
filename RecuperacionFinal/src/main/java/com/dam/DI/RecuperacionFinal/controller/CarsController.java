package com.dam.DI.RecuperacionFinal.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dam.DI.RecuperacionFinal.model.Car;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;



public class CarsController {
    @FXML private TextField txtSearch; // Cuadro de busqueda
    @FXML private FlowPane carsContainer; // Contenedor de las tarjetas
    @FXML private ObservableList<Car> carObservableList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        // Lista de prueba
    	List<Car> mockCars = new ArrayList<>();
    	mockCars.add(new Car(1, "Toyota", "Yaris", 116, "Compact", LocalDateTime.now(), "", false));
    	mockCars.add(new Car(2, "Ford", "Mustang", 450, "Sports", LocalDateTime.now(), "", true));
    	mockCars.add(new Car(3, "Audi", "A4", 204, "Sedan", LocalDateTime.now(), "", false));
    	
    	carObservableList.addAll(mockCars);
    	
    	// Lista filtrada vinculada a la lista observable
    	FilteredList<Car> filteredCars = new FilteredList<>(carObservableList, p -> true);
    	
    	// Se añade un Listener
    	txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
    		// Se modifica el filtro cada vez que el usuario teclea
    		filteredCars.setPredicate( car -> {
    			// Cuadro de busqueda vacio, por lo tanto se muestran todos los coches
    			if (newValue == null || newValue.isEmpty()) {
    				return true;
    			}
    			
    			// Se pasa el texto introducido siempre a minusculas
    			String lowerCaseFilter = newValue.toLowerCase();
    			
    			// Se comprueba si la marca, y la potencia coinciden
    			if (car.getBrand().toLowerCase().contains(lowerCaseFilter)) {
    				return true;
    			} else if (String.valueOf(car.getHorsePower()).contains(lowerCaseFilter)) {
    				return true;
    			}
    			// No coincide con los criterios entonces el coche queda fuera del filtro
    			return false;
    		});
    		renderCarCards(filteredCars);
    	});
    	renderCarCards(filteredCars);
    }
    	
	private void renderCarCards(List<Car> carsToRender) {
		// Se limpian las tarjetas
		carsContainer.getChildren().clear();
	
    	// Se cargan e  inyectan en cada tarjeta
    	for (Car car : carsToRender) {
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
