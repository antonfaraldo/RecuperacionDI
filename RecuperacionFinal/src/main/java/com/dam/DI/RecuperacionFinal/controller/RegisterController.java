package com.dam.DI.RecuperacionFinal.controller;

import com.dam.DI.RecuperacionFinal.dao.UserDAO;
import com.dam.DI.RecuperacionFinal.dao.UserDAOImpl;
import com.dam.DI.RecuperacionFinal.model.User;
import com.dam.DI.RecuperacionFinal.util.AppShell;
import com.dam.DI.RecuperacionFinal.util.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
	@FXML private TextField txtUsername;
	@FXML private TextField txtEmail;
	@FXML private PasswordField txtPassword;
	@FXML private PasswordField txtConfirmPassword;
	
	@FXML private Label lblStatus;

    private final UserDAO userDAO =  new UserDAOImpl();
	
	@FXML
	private void handleRegister() {
		String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        
        // Se comprueba si los campos estan vacios
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
        	lblStatus.setText("Por favor, rellena todos los campos obligatorios.");
        	return;
        }
        // Se valida la longitud minima de 6 caracteres
        if (password.length() < 6) {
        	lblStatus.setText("La contraseña debe tener al menos 6 caracteres.");
            return;
        }
        // Se valida que contenga letras y numeros
        if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
        	lblStatus.setText("La contraseña debe contener obligatoriamente letras y números");
        	return;
        }
        // Se valida que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
        	lblStatus.setText("Las contraseñas introducidas no coinciden.");
        	return;
        }
        User newUser = new User(username, email, password, "user");

        boolean success = userDAO.registerUser(newUser);
        if (success) {
            System.out.println(username + " registrado correctamente.");
            navigateToLogin();
        } else {
            lblStatus.setText("El nombre de usuario ya existe.");
        }
	}

	@FXML
	private void navigateToLogin() {
		// TODO Auto-generated method stub
		AppShell.getInstance().loadView(View.LOGIN);
        AppShell.getInstance().adjustWindow();
	}

}
