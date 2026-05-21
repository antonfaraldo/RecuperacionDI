package com.dam.DI.RecuperacionFinal.controller;

import com.dam.DI.RecuperacionFinal.dao.UserDAO;
import com.dam.DI.RecuperacionFinal.dao.UserDAOImpl;
import com.dam.DI.RecuperacionFinal.model.User;
import com.dam.DI.RecuperacionFinal.util.PasswordUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;


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

        btnDelete.setOnAction(event -> {
            User selectedUser = usersTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                showAlerDialog(Alert.AlertType.WARNING, "Acción Requerida", "Selecciona un usuario de la tabla para eliminarlo");
                return;
            }
            boolean confirmed = showConfirmDialog("Confirmar Borrado","Estas seguro de que quieres borrar permanentemente al usuario: " + selectedUser.getUsername() + "?");
            if (confirmed) {
                if (userDAO.deleteUser(selectedUser.getId())) {
                    userObservableList.remove(selectedUser);
                    showAlerDialog(Alert.AlertType.INFORMATION, "Exito", "Usuario eliminado con exito");
                } else {
                    showAlerDialog(Alert.AlertType.ERROR, "Error", "Error al borrar el usuario");
                }
            }
        });

        btnChangeRole.setOnAction(event -> {
            User selectedUser = usersTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                showAlerDialog(Alert.AlertType.WARNING, "Acción Requerida", "Por favor, selecciona el usuario que quieres cambiar el rol");
                return;
            }
            ChoiceDialog<String> roleDialog = new ChoiceDialog<>(selectedUser.getRole(), "user", "admin");
            roleDialog.setTitle("Selecciona el rol");
            roleDialog.setHeaderText("Modifica el rol de: " + selectedUser.getUsername());
            roleDialog.setContentText("Selecciona el nuevo rol");

            Optional<String> result = roleDialog.showAndWait();
            result.ifPresent(newRole -> {
                if (userDAO.updateUserRole(selectedUser.getId(), newRole)) {
                    selectedUser.setRole(newRole);
                    usersTable.refresh();
                    showAlerDialog(Alert.AlertType.INFORMATION, "Exito", "Rol actualizado exitosamente a " + newRole);
                }
            });
        });

        btnModify.setOnAction(event -> {
            User selectedUser = usersTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                showAlerDialog(Alert.AlertType.WARNING, "Acción Requerida", "Por favor selecciona el usuario a editar");
                return;
            }
            TextInputDialog usernameDialog = new TextInputDialog(selectedUser.getUsername());
            usernameDialog.setTitle("Modifica el usuario");
            usernameDialog.setHeaderText("Actualizar detalles de la cuenta con ID: " + selectedUser.getId());
            usernameDialog.setContentText("Introduzca el neuvo nombre del usuario");

            Optional<String> usernameOpt = usernameDialog.showAndWait();
            if (usernameOpt.isPresent() && !usernameOpt.get().trim().isEmpty()) {
                String newUsername = usernameOpt.get().trim();

                TextInputDialog emailDialog = new TextInputDialog(selectedUser.getEmail());
                emailDialog.setTitle("Modifica el usuario");
                emailDialog.setHeaderText("Actualizando detalles de la cuenta con ID: " + selectedUser.getId());
                emailDialog.setContentText("Introduzca el nuevo email del usuario");

                Optional<String> emailOpt = emailDialog.showAndWait();
                if (emailOpt.isPresent() && !emailOpt.get().trim().isEmpty()) {
                    String newEmail = emailOpt.get().trim();

                    if (userDAO.updateUserDetails(selectedUser.getId(), newUsername, newEmail)) {
                        selectedUser.setUsername(newUsername);
                        selectedUser.setEmail(newEmail);
                        usersTable.refresh();
                        showAlerDialog(Alert.AlertType.INFORMATION, "Exito", "Usuario actualizado con exito");
                    }
                }
            }
        });

        btnCreate.setOnAction(event -> {
            TextInputDialog userDialog = new TextInputDialog();
            userDialog.setTitle("Crear usuario");
            userDialog.setHeaderText("Registrando una nueva cuenta");
            userDialog.setContentText("Introduzca el nombre del usuario:");

            Optional<String> usernameOpt = userDialog.showAndWait();
            if (usernameOpt.isPresent() && !usernameOpt.get().trim().isEmpty()) {
                String username = usernameOpt.get().trim();

                TextInputDialog emailDialog = new TextInputDialog();
                emailDialog.setTitle("Crear usuario");
                emailDialog.setContentText("Introduzca el email del usuario:");
                Optional<String> emailOpt = emailDialog.showAndWait();

                if (emailOpt.isPresent() && !emailOpt.get().trim().isEmpty()) {
                    String email = emailOpt.get().trim();

                    TextInputDialog passDialog = new  TextInputDialog();
                    passDialog.setTitle("Crear usuario");
                    passDialog.setContentText("Introduzca la contraseña del usuario:");
                    Optional<String> passOpt = passDialog.showAndWait();

                    if (passOpt.isPresent() && !passOpt.get().trim().isEmpty()) {
                        String password = passOpt.get().trim();

                        if (password.length() < 6) {
                            showAlerDialog(Alert.AlertType.WARNING, "Contraseña Débil", "La contraseña debe tener al menos 6 caracteres.");
                            return;
                        }
                        if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
                            showAlerDialog(Alert.AlertType.WARNING, "Contraseña Débil", "La contraseña debe contener obligatoriamente letras y números");
                            return;
                        }

                        String securedHash = PasswordUtil.hashPassword(password);
                        User newUser = new User(username, email, securedHash, "user");

                        if (userDAO.registerUser(newUser)) {
                            showAlerDialog(Alert.AlertType.INFORMATION, "Exito", "Usuario registrado con exito");
                            refreshTableData();
                        } else {
                            showAlerDialog(Alert.AlertType.ERROR, "Error", "Error al registrar usuario");
                        }
                    }
                }
            }
        });
        System.out.println("Users administration table layout loaded successfully.");
    }

    private void refreshTableData() {
        userObservableList.clear();
        List<User> databaseUsers = userDAO.getAllUsers();
        userObservableList.addAll(databaseUsers);
    }

    private void showAlerDialog(Alert.AlertType alertType, String tittle, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(tittle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
