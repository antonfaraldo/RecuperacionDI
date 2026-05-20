package com.dam.DI.RecuperacionFinal.controller;

import com.dam.DI.RecuperacionFinal.util.AppShell;
import com.dam.DI.RecuperacionFinal.util.View;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    private StackPane contentArea; // Contenedor central
    @FXML
    private Button btnUsers;
    @FXML
    public void initialize() {
    	Platform.runLater(() -> {
    		AppShell.getInstance().loadView(View.CARS);
    	});
    }

    @FXML
    private void showCars() {
        AppShell.getInstance().loadView(View.CARS);
    }
    @FXML
    private void showUsers() {
        AppShell.getInstance().loadView(View.USERS);
    }
    @FXML
    private void handleLogout() {
        System.out.println("Loggin out...");
    }
}
