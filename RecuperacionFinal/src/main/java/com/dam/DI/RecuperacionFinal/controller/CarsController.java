package com.dam.DI.RecuperacionFinal.controller;

import java.io.IOException; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dam.DI.RecuperacionFinal.dao.CarDAO;
import com.dam.DI.RecuperacionFinal.dao.CarDAOImpl;
import com.dam.DI.RecuperacionFinal.model.Car;

import com.dam.DI.RecuperacionFinal.model.User;
import com.dam.DI.RecuperacionFinal.util.AppShell;
import com.dam.DI.RecuperacionFinal.util.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;



public class CarsController {
    @FXML private TextField txtSearch; // Cuadro de busqueda
    @FXML private FlowPane carsContainer; // Contenedor de las tarjetas
    @FXML private Button btnAddCar;
    @FXML private ObservableList<Car> carObservableList = FXCollections.observableArrayList();

    private final CarDAO carDAO = new CarDAOImpl();
    
    @FXML
    public void initialize() {
         User currentUser = AppShell.getInstance().getSessionUser();
         int currentUserId = (currentUser != null) ?  currentUser.getId() : 0;

         if (currentUser != null) {
             if (!"admin".equals(currentUser.getRole())) {
                 btnAddCar.setVisible(false);
                 btnAddCar.setManaged(false);
             }
         }

         List<Car> realCars = carDAO.getAllCars(currentUserId);
         carObservableList.addAll(realCars);

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

                return car.getType().toLowerCase().contains(lowerCaseFilter);
    		});
    		renderCarCards(filteredCars);
    	});
        btnAddCar.setOnAction(event -> {
            AppShell.getInstance().loadView(View.CREATECAR);
        });
        renderCarCards(filteredCars);
    }
    	
	private void renderCarCards(List<Car> carsToRender) {
		// Se limpian las tarjetas
		carsContainer.getChildren().clear();
	
    	// Se cargan e  inyectan en cada tarjeta
    	for (Car car : carsToRender) {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card_view.fxml"));
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
