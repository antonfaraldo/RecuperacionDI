package com.dam.DI.RecuperacionFinal.controller;


import com.dam.DI.RecuperacionFinal.dao.CarDAO;
import com.dam.DI.RecuperacionFinal.dao.CarDAOImpl;
import com.dam.DI.RecuperacionFinal.model.Car;

import com.dam.DI.RecuperacionFinal.model.User;
import com.dam.DI.RecuperacionFinal.util.AppShell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class CardController {
	@FXML private Label lblBrandModel; // Marca y Modelo
	@FXML private Label lblSpecs; // Especificaciones de potencia de motor y tipo de coche
   @FXML private Label lblRegistrationDate;
	@FXML private ToggleButton btnFavorite;

    @FXML private HBox adminActionsContainer;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;

    private CarDAO carDAO = new CarDAOImpl();
    private Car currentCar;
	
	public void setCarData(Car car) {
		this.currentCar = car;
		lblBrandModel.setText(car.getBrand() + " " + car.getModel());
		lblSpecs.setText(car.getHorsePower() + " HP - " + car.getType());

        if (car.getRegistrationDate() != null) {
            lblRegistrationDate.setText("Registrado: " + car.getRegistrationDate().toLocalDate().toString());
        }

		btnFavorite.setSelected(car.isFavorite());

        btnFavorite.setText(car.isFavorite() ? "❤ Favorited" : "🖤 Add Favorite");

        User currentUser = AppShell.getInstance().getSessionUser();
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            adminActionsContainer.setVisible(false);
            adminActionsContainer.setManaged(false);
        }
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

        btnDelete.setOnAction(e -> System.out.println("Admin solicita eliminar el coche con ID: " + currentCar.getId()));
        btnUpdate.setOnAction(e -> System.out.println("Admin solicita editar el coche con ID: " + currentCar.getId()));

    }

}
