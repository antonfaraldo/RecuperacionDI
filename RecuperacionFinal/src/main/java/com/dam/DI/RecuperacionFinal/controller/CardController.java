package com.dam.DI.RecuperacionFinal.controller;


import com.dam.DI.RecuperacionFinal.dao.CarDAO;
import com.dam.DI.RecuperacionFinal.dao.CarDAOImpl;
import com.dam.DI.RecuperacionFinal.model.Car;

import com.dam.DI.RecuperacionFinal.model.User;
import com.dam.DI.RecuperacionFinal.util.AppShell;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class CardController {
    @FXML private VBox cardRoot;
    @FXML private ImageView imgCar;
	@FXML private Label lblBrandModel; // Marca y Modelo
	@FXML private Label lblSpecs; // Especificaciones de potencia de motor y tipo de coche
   @FXML private Label lblRegistrationDate;
	@FXML private ToggleButton btnFavorite;

    @FXML private HBox adminActionsContainer;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;

    private CarDAO carDAO = new CarDAOImpl();
    private Car currentCar;
    private Runnable refreshCallback;

    public void setRefreshCallback(Runnable callback) {
        this.refreshCallback = callback;
    }
	
	public void setCarData(Car car, boolean isMostPopular) {
		this.currentCar = car;
		lblBrandModel.setText(car.getBrand() + " " + car.getModel());
		lblSpecs.setText(car.getHorsePower() + " HP - " + car.getType());

        if (car.getRegistrationDate() != null) {
            lblRegistrationDate.setText("Registrado: " + car.getRegistrationDate().toLocalDate().toString());
        }

		btnFavorite.setSelected(car.isFavorite());
        btnFavorite.setText(car.isFavorite() ? "❤ Favorito" : "🖤 Añadir como Favorito");

        if (car.getImageUrl() != null && !car.getImageUrl().trim().isEmpty()) {
            try {
              imgCar.setImage(new Image(car.getImageUrl(), true));
            } catch (Exception e){
                imgCar.setImage(new Image("https://placehold.co/400x250?text=No+Image"));
            }
        } else {
            imgCar.setImage(new Image("https://placehold.co/400x250?text=No+Image"));
        }

        cardRoot.getStyleClass().removeAll("favorite-card", "most-popular-card");

        // el coche favorito de cada usuario tiene el borde verde
        if (car.isFavorite()) {
            cardRoot.getStyleClass().add("favorite-card");
        }

        // el coche mas popular tiene el bord oro y una corona
        if (isMostPopular) {
            cardRoot.getStyleClass().add("most-popular-card");
            lblBrandModel.setText("👑 " + car.getBrand() + " " + car.getModel() + " [LÍDER]");
        }

        User currentUser = AppShell.getInstance().getSessionUser();
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            adminActionsContainer.setVisible(false);
            adminActionsContainer.setManaged(false);
        }
	}

    @FXML
    public  void initialize() {
        btnFavorite.setOnAction(event -> {
            User currentUser = AppShell.getInstance().getSessionUser();

            if (currentUser != null && currentCar != null) {
                boolean newState = btnFavorite.isSelected();
                boolean success = carDAO.toggleFavorite(currentUser.getId(), currentCar.getId(), newState);

                if (success) {
                    currentCar.setFavorite(newState);
                    System.out.println("Coche ID " +  currentCar.getId() + " actualizado como favorito: " + newState);
                    btnFavorite.setText(newState ? "❤ Favorited" : "🖤 Add Favorite");

                    if (refreshCallback != null) {
                        refreshCallback.run();
                    }
                } else {
                    btnFavorite.setSelected(!newState);
                }
            }
        });

        btnDelete.setOnAction(e -> {
            if (currentCar == null) return;

                Alert confirmAlert =  new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmar Borrado");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("¿Está seguro de que desea eliminar permanentemente el coche: " + currentCar.getBrand() + " " + currentCar.getModel() + "?");

                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (carDAO.deleteCar(currentCar.getId())) {
                        showAlertDialog(Alert.AlertType.INFORMATION, "Éxito", "Vehículo eliminado correctamente del catálogo.");
                        if (refreshCallback != null) {
                            refreshCallback.run();
                        }
                    } else {
                        showAlertDialog(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el vehículo de la base de datos.");
                    }
                }
        });
        btnUpdate.setOnAction(e -> {
            if (currentCar == null) return;

            TextInputDialog brandDialog = new TextInputDialog(currentCar.getBrand());
            brandDialog.setTitle("Editar Vehículo");
            brandDialog.setHeaderText("Modificando registro ID: " + currentCar.getId());
            brandDialog.setContentText("Nueva Marca:");

            Optional<String> brandOpt = brandDialog.showAndWait();
            if (brandOpt.isPresent() && !brandOpt.get().trim().isEmpty()) {
                String newBrand = brandOpt.get().trim();

                TextInputDialog modelDialog = new TextInputDialog(currentCar.getModel());
                modelDialog.setTitle("Editar Vehículo");
                modelDialog.setContentText("Nuevo Modelo:");

                Optional<String> modelOpt = modelDialog.showAndWait();
                if (modelOpt.isPresent() && !modelOpt.get().trim().isEmpty()) {
                    String newModel = modelOpt.get().trim();

                    TextInputDialog hpDialog = new TextInputDialog(String.valueOf(currentCar.getHorsePower()));
                    hpDialog.setTitle("Editar Vehículo");
                    hpDialog.setContentText("Nueva Potencia (HP):");

                    Optional<String> hpOpt = hpDialog.showAndWait();
                    if (hpOpt.isPresent() && !hpOpt.get().trim().isEmpty()) {

                        ChoiceDialog<String> typeDialog = new ChoiceDialog<>(currentCar.getType(), "Deportivo", "Berlina", "Compacto", "SUV", "Coupé");
                        typeDialog.setTitle("Editar Vehículo");
                        typeDialog.setContentText("Nuevo Tipo Segmento:");

                        Optional<String> typeOpt = typeDialog.showAndWait();
                        if (typeOpt.isPresent()) {
                            TextInputDialog imgDialog = new TextInputDialog(currentCar.getImageUrl() != null ?  currentCar.getImageUrl() : "");
                            imgDialog.setTitle("Editar Vehículo");
                            imgDialog.setContentText("Nueva URL de Imagen:");

                            Optional<String> imgOpt = imgDialog.showAndWait();
                            if (imgOpt.isPresent()) {
                            try {
                                int newHp = Integer.parseInt(hpOpt.get().trim());
                                String newType = typeOpt.get();
                                String newImg = imgOpt.get().trim().isEmpty() ? null : imgOpt.get().trim();

                                if (carDAO.updateCar(currentCar.getId(), newBrand, newModel, newHp, newType, newImg)) {
                                    Alert inforAlert = new Alert (Alert.AlertType.INFORMATION, "Vehículo actualizado con éxito.", ButtonType.OK);
                                    inforAlert.setTitle("Exito");
                                    inforAlert.setHeaderText(null);
                                    inforAlert.showAndWait();
                                    if (refreshCallback != null) {
                                        refreshCallback.run();
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "La potencia debe ser un número entero válido.", ButtonType.OK);
                                errorAlert.setTitle("Error");
                                errorAlert.setHeaderText(null);
                                errorAlert.showAndWait();
                            }
                            }
                        }
                    }
                }
            }
        });

    }

    private void showAlertDialog(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
