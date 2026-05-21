package com.dam.DI.RecuperacionFinal.controller;

import com.dam.DI.RecuperacionFinal.dao.CarDAO;
import com.dam.DI.RecuperacionFinal.dao.CarDAOImpl;
import com.dam.DI.RecuperacionFinal.model.Car;
import com.dam.DI.RecuperacionFinal.util.AppShell;
import com.dam.DI.RecuperacionFinal.util.View;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CreateCarController {
    @FXML
    private TextField txtBrand;
    @FXML private TextField txtModel;
    @FXML private TextField txtHorsePower;
    @FXML private ComboBox<String> cmbType;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final CarDAO carDAO = new CarDAOImpl();

    @FXML
    public void initialize() {
        cmbType.getItems().addAll("Deportivo", "Berlina", "Compacto", "SUV", "Coupé");
        btnCancel.setOnAction(e -> AppShell.getInstance().loadView(View.CARS));

        btnSave.setOnAction(e -> {
            String brand = txtBrand.getText().trim();
            String model = txtModel.getText().trim();
            String horsePower = txtHorsePower.getText().trim();
            String type = cmbType.getValue();

            if (brand.isEmpty() || model.isEmpty() || horsePower.isEmpty() || type == null ) {
                showModalAlert(Alert.AlertType.WARNING, "Campos Obligatorios", "Por favor rellena todos los campos");
                return;
            }
            try {
                int hp = Integer.parseInt(horsePower);

                Car newCar = new Car();
                newCar.setBrand(brand);
                newCar.setModel(model);
                newCar.setHorsePower(hp);
                newCar.setType(type);

                if (carDAO.createCar(newCar)) {
                    showModalAlert(Alert.AlertType.INFORMATION, "Éxito", "Vehículo añadido correctamente al catálogo.");
                    AppShell.getInstance().loadView(View.CARS);
                } else {
                    showModalAlert(Alert.AlertType.ERROR, "Error de Base de Datos", "No se pudo guardar el registro del coche en el servidor.");
                }
            } catch (NumberFormatException exception) {
                showModalAlert(Alert.AlertType.ERROR, "Valor Inválido", "El campo de potencia debe contener un número entero válido.");
            }
        });
    }

    private void showModalAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
