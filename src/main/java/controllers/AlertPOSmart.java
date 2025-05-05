package controllers;

import javafx.scene.control.Alert;

public class AlertPOSmart extends Alert {
    public AlertPOSmart(AlertType alertType, String titulo, String mensaje) {
        super(alertType);
        System.out.println("Se llamo nueva clase");
        setTitulo(titulo);
        setMensaje(mensaje);
        setHeaderText(null);
        showAndWait();
    }

    void setMensaje(String mensaje){
        this.setContentText(mensaje);
    }

    void setTitulo(String titulo){
        this.setTitle(titulo);
    }
}
