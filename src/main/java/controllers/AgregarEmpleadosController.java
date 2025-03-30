package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AgregarEmpleadosController implements Initializable {

    @FXML
    private ComboBox<String> rolOpciones;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> rol = FXCollections.observableArrayList("Empleado", "Administrador");
        rolOpciones.setItems(rol);
    }
}
