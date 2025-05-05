package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    private void handleButtonUbicaciones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/agregarUbicacion.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/agregarCategoria.fxml"));
            Parent root = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAgregarProductos(ActionEvent event) {
        System.out.println("Clic en 'Agregar Producto'");
        try {
            System.out.println("entra try'");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Productos.fxml"));
            Parent root = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("entra a catch");
            e.printStackTrace();
        }
    }

}