package controllers;

import BaseDatos.BaseDatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AgregarEmpleadosController implements Initializable {
    BaseDatos mBD = new BaseDatos();

    @FXML
    private ComboBox<String> rolOpciones;

    @FXML
    private TextField id;

    @FXML
    private TextField nombre;

    @FXML
    private TextField contraseña;

    @FXML
    private TextField nombre_completo;

    @FXML
    private TextField telefono;

    @FXML
    private TextField ciudad;

    @FXML
    private TextField direccion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> rol = FXCollections.observableArrayList("Empleado", "Administrador");
        rolOpciones.setItems(rol);

        nombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                nombre.setText(oldValue);
            }
        });

        nombre_completo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                nombre_completo.setText(oldValue);
            }
        });

        telefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telefono.setText(oldValue);
            }
        });

        ciudad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                ciudad.setText(oldValue);
            }
        });
    }

    @FXML
    void handleAgregarEmpleado(ActionEvent event) {
        int nuevoid = Integer.parseInt(id.getText());
        String nuevoNombre = nombre.getText();
        String nuevoContraseña = contraseña.getText();
        String nuevoNombreCom = nombre_completo.getText();
        String nuevoRol = rolOpciones.getValue();
        String nuevoTelefono = telefono.getText();
        String nuevoCiudad = ciudad.getText();
        String nuevoDireccion = direccion.getText();

        boolean seAñadio = mBD.agregarEmpleado(nuevoid, nuevoNombre, nuevoContraseña, nuevoNombreCom, nuevoRol, nuevoTelefono, nuevoCiudad, nuevoDireccion);

        if (seAñadio) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exito");
            alert.setHeaderText(null);
            alert.setContentText("Se agrego con exito al empleado " + nuevoNombre);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fallo");
        alert.setHeaderText(null);
        alert.setContentText("Algo salio mal al intentar agregar al empleado " + nuevoNombre);
        alert.showAndWait();
    }
}
