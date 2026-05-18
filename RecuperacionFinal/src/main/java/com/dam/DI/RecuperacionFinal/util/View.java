package com.dam.DI.RecuperacionFinal.util;

public enum View {
    MAIN("/main-view.fxml"),
    CARS("/cars_view.fxml"),
    USERS("/users_view.fxml");


    private final String fxmlPath;

    View (String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }
    public String getFxmlPath() {
        return fxmlPath;
    }
}
