package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AccesosRapidosController {

    @FXML
    private AnchorPane rootPane; // Asegúrate de que este sea el contenedor principal del archivo FXML.

    @FXML
    private Button buttonProveedores;

    @FXML
    private void handleButtonProveedores(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BuscarProveedores.fxml"));
            Parent root = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonEmpleado(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionEmpleado.fxml"));
            Parent root = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAgregarProductos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Productos.fxml"));
            Parent root = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonUbicaciones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionarUbicacion.fxml"));
            Parent root = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonCategoria(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionarCategoria.fxml"));
            Parent root = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleButtonHistorial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/historialVentas.fxml"));
            Parent root = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}