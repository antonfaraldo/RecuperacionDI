package com.dam.DI.RecuperacionFinal.controller;

import com.dam.DI.RecuperacionFinal.util.AppShell;
import com.dam.DI.RecuperacionFinal.util.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	@FXML private TextField txtUsername;
	@FXML private PasswordField txtPassword;
	@FXML private Label lblError;
	
	@FXML
	private void handleLogin() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		
		// Validacion provisional
		if ("admin".equals(username) && "admin123".equals(password)) {
			AppShell.getInstance().loadView(View.MAIN);
			AppShell.getInstance().adjustWindow();
		} else {
			lblError.setText("Usuario o contraseña incorrectos.");
		}
	}
	@FXML
	private void navigateToRegister() {
		AppShell.getInstance().loadView(View.REGISTER);
		AppShell.getInstance().adjustWindow();
	}

}
