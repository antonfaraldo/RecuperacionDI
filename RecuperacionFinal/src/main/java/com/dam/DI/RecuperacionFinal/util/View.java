package com.dam.DI.RecuperacionFinal.util;

public enum View {
    MAIN("fxml/main-view.fxml"),
    CARS("fxml/cars_view.fxml"),
    USERS("fxml/users_view.fxml"),
	CARD("fxml/card_view.fxml"),
	LOGIN("fxml/login.fxml"),
	REGISTER("fxml/register.fxml");


    private final String fxmlPath;

    View (String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }
    public String getFxmlPath() {
        return fxmlPath;
    }
}
