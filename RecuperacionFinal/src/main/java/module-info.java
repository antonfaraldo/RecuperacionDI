module com.dam.DI.RecuperacionFinal {
    requires javafx.controls;
    requires javafx.fxml;

    // Abre el paquete raíz al cargador de FXML de JavaFX
    opens com.dam.DI.RecuperacionFinal to javafx.fxml;

    // Abre tus paquetes internos para que JavaFX pueda enlazar tus controladores y el AppShell
    opens com.dam.DI.RecuperacionFinal.controller to javafx.fxml;
    opens com.dam.DI.RecuperacionFinal.util to javafx.fxml;

    // Exporta el paquete principal para que la máquina virtual de Java inicie la aplicación
    exports com.dam.DI.RecuperacionFinal;
}