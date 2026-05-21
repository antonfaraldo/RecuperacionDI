package com.dam.DI.RecuperacionFinal.controller;

import com.dam.DI.RecuperacionFinal.model.User;
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
        User sessionUser = AppShell.getInstance().getSessionUser();

        if (sessionUser != null){
            // Si el usuario no es admin se oculta fisicamente el btn
            if (!"admin".equals(sessionUser.getRole())){
                btnUsers.setVisible(false);
                btnUsers.setManaged(false);
            }
        }

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
        AppShell.getInstance().setSessionUser(null);
        AppShell.getInstance().loadView(View.LOGIN);
        AppShell.getInstance().adjustWindow();
    }
}
