package com.dam.DI.RecuperacionFinal.controller;

import com.dam.DI.RecuperacionFinal.dao.UserDAO;
import com.dam.DI.RecuperacionFinal.dao.UserDAOImpl;
import com.dam.DI.RecuperacionFinal.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UsersController {
    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colRole;

    private final UserDAO userDAO = new UserDAOImpl();
private final ObservableList<User> userObservableList =  FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        List<User> databaseUsers = userDAO.getAllUsers();
        userObservableList.addAll(databaseUsers);
        usersTable.setItems(userObservableList);
        System.out.println("Users administration table layout loaded successfully.");
    }
}
