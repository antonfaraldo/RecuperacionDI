package com.dam.DI.RecuperacionFinal.util;

import com.dam.DI.RecuperacionFinal.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppShell {
    private static AppShell instance;
    private Stage primaryStage;
    private Map<View, Object> controllers = new HashMap<>();
    private User sessionUser;


    private AppShell() {}

    public static AppShell getInstance() {
        if (instance == null) {
            instance = new AppShell();
        }
        return instance;
    }

    public void init(Stage stage) {
        this.primaryStage = stage;

        Scene scene = new Scene(new StackPane(), 900, 600);
        stage.setScene(scene);
        stage.setTitle("Gestor de Coches");
        stage.show();
    }
    public Object loadView(View view) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + view.getFxmlPath()));
        try {
            Parent viewNode = loader.load();
            Object controller = loader.getController();
            controllers.put(view, controller);

            if (view == View.MAIN || view == View.LOGIN || view == View.REGISTER) {
                primaryStage.setScene(new Scene(viewNode));
            } else {
                Parent currentroot = primaryStage.getScene().getRoot();
                if (currentroot instanceof BorderPane) {
                    BorderPane mainBorderPane = (BorderPane) currentroot;
                    StackPane contentArea = (StackPane) mainBorderPane.getCenter();
                    if (contentArea != null) {
                        contentArea.getChildren().setAll(viewNode);
                    }
                } else {
                    primaryStage.getScene().setRoot(viewNode);
                }
            }
            return controller;
        } catch (IOException e) {
            throw new RuntimeException("Error loading view: " + e);
        }
    }

    public void adjustWindow() {
        if (primaryStage != null) {
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        }
    }

    public Map<View, Object> getControllers() {
        return controllers;
    }

    public void setControllers(Map<View, Object> controllers) {
        this.controllers = controllers;
    }

    public static void setInstance(AppShell instance) {
        AppShell.instance = instance;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }
}
