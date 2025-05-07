package controllers;

import BaseDatos.BaseDatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class EditarEmpleadoController implements Initializable {

    BaseDatos mBD = new BaseDatos();

    @FXML private TableView<Empleado> tablaEmpleados;
    @FXML private TableColumn<Empleado, Integer> colId;
    @FXML private TableColumn<Empleado, String> colNombre;
    @FXML private TableColumn<Empleado, String> colNombreCompleto;
    @FXML private TableColumn<Empleado, String> colRol;
    @FXML private TableColumn<Empleado, String> colTelefono;

    @FXML private ComboBox<String> rolOpciones;
    @FXML private TextField id, nombre, contraseña, nombre_completo, telefono, ciudad, direccion;

    ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    AlertPOSmart alerta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> rol = FXCollections.observableArrayList("Empleado", "Administrador");
        rolOpciones.setItems(rol);

        // Validaciones
        nombre.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) nombre.setText(oldValue);
        });
        nombre_completo.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) nombre_completo.setText(oldValue);
        });
        telefono.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) telefono.setText(oldValue);
        });
        ciudad.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) ciudad.setText(oldValue);
        });

        configurarTabla();
        cargarEmpleados();

        tablaEmpleados.setOnMouseClicked(e -> {
            Empleado emp = tablaEmpleados.getSelectionModel().getSelectedItem();
            if (emp != null) {
                setCamposEmpleado(emp.getId(), emp.getNombre(), emp.getContraseña(), emp.getNombreCompleto(), emp.getRol(), emp.getTelefono(), emp.getCiudad(), emp.getDireccion());
            }
        });
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    private void cargarEmpleados() {
        empleados.clear();
        empleados.addAll(mBD.obtenerEmpleados());
        tablaEmpleados.setItems(empleados);
    }

    public void setCamposEmpleado(int idEmpleado, String nom, String pass, String nomComp, String rol,
                                  String tel, String ciu, String dir) {
        id.setText(String.valueOf(idEmpleado));
        nombre.setText(nom);
        contraseña.setText(pass);
        nombre_completo.setText(nomComp);
        rolOpciones.setValue(rol);
        telefono.setText(tel);
        ciudad.setText(ciu);
        direccion.setText(dir);
    }

    @FXML
    void handleActualizarEmpleado(ActionEvent event) {
        int nuevoId = Integer.parseInt(id.getText());
        String nuevoNombre = nombre.getText();
        String nuevoContraseña = contraseña.getText();
        String nuevoNombreCom = nombre_completo.getText();
        String nuevoRol = rolOpciones.getValue();
        String nuevoTelefono = telefono.getText();
        String nuevoCiudad = ciudad.getText();
        String nuevoDireccion = direccion.getText();

        boolean actualizado = mBD.editarEmpleado(nuevoId, nuevoNombre, nuevoContraseña, nuevoNombreCom,
                nuevoRol, nuevoTelefono, nuevoCiudad, nuevoDireccion);

        if (actualizado) {
            mostrarAlerta("Actualización Exitosa", "Se actualizó el empleado correctamente.");
            cargarEmpleados();
        } else {
            mostrarAlerta("Error", "No se pudo actualizar el empleado.");
        }
    }

    @FXML
    void handleEliminarEmpleado(ActionEvent event) {
        Empleado seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            boolean eliminado = mBD.eliminarEmpleado(seleccionado.getId());
            if (eliminado) {
                mostrarAlerta("Eliminado", "Empleado eliminado correctamente.");
                cargarEmpleados();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el empleado.");
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        alerta = new AlertPOSmart(Alert.AlertType.INFORMATION, titulo, mensaje);
    }
}
