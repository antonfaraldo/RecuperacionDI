package com.dam.DI.RecuperacionFinal;

import com.dam.DI.RecuperacionFinal.util.AppShell;
import com.dam.DI.RecuperacionFinal.util.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start (Stage primaryStage) throws Exception {
        AppShell.getInstance().init(primaryStage);
        AppShell.getInstance().loadView(View.LOGIN);
        AppShell.getInstance().adjustWindow();
    }
    public static void main( String[] args ) {
        launch(args);
    }
}
