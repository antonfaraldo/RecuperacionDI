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

public class LoginController {
	@FXML private TextField txtUsername;
	@FXML private PasswordField txtPassword;
	@FXML private Label lblError;

    private final UserDAO userDAO =  new UserDAOImpl();
	
	@FXML
	private void handleLogin() {
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Rellena todos los campos");
            return;
        }
        User loggedUser = userDAO.loginUser(username, password);

        if (loggedUser != null) {
            AppShell.getInstance().setSessionUser(loggedUser);
            AppShell.getInstance().loadView(View.MAIN);
            AppShell.getInstance().adjustWindow();
        } else {
            lblError.setText("Usuario o contraseña incorrectos");
        }
	}
	@FXML
	private void navigateToRegister() {
		AppShell.getInstance().loadView(View.REGISTER);
		AppShell.getInstance().adjustWindow();
	}

}
