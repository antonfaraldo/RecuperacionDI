module com.dam.DI.RecuperacionFinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    opens com.dam.DI.RecuperacionFinal to javafx.fxml;
    opens com.dam.DI.RecuperacionFinal.controller to javafx.fxml;
    opens com.dam.DI.RecuperacionFinal.util to javafx.fxml;

    
    exports com.dam.DI.RecuperacionFinal;
}