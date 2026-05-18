package com.dam.DI.RecuperacionFinal.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;



public class CarsController {
    @FXML
    private TextField txtSearch;
    @FXML
    private FlowPane carsContainer;
    @FXML
    public void initialize() {
        System.out.println("Cars view layout loaded successfully.");
    }
}
