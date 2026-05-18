package com.dam.DI.RecuperacionFinal.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UsersController {
    @FXML
    private TableView<?> usersTable;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    public void initialize() {
        System.out.println("Users administration table layout loaded successfully.");
    }
}
