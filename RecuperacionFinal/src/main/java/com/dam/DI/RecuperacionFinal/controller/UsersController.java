package com.dam.DI.RecuperacionFinal.controller;

import com.dam.DI.RecuperacionFinal.dao.UserDAO;
import com.dam.DI.RecuperacionFinal.dao.UserDAOImpl;
import com.dam.DI.RecuperacionFinal.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UsersController {
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colPassword;
    @FXML private TableColumn<User, String> colRole;
    @FXML private TableColumn<User, Integer> colFavoriteCarId;

    @FXML private TextField txtSearch;
    @FXML private Button btnCreate;
    @FXML private Button btnModify;
    @FXML private Button btnDelete;
    @FXML private Button btnChangeRole;

    private final UserDAO userDAO = new UserDAOImpl();
    private final ObservableList<User> userObservableList =  FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colFavoriteCarId.setCellValueFactory(new PropertyValueFactory<>("favoriteCarId"));

        List<User> databaseUsers = userDAO.getAllUsers();
        userObservableList.addAll(databaseUsers);

        FilteredList<User> filteredUsers = new FilteredList<>(userObservableList, p -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if  (user.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getRole().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        usersTable.setItems(filteredUsers);
        System.out.println("Users administration table layout loaded successfully.");
    }
}
