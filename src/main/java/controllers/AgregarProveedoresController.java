package controllers;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import BaseDatos.BaseDatos;
import javafx.fxml.FXML;

import java.util.List;

public class AgregarProveedoresController {

    @FXML
    private TextField id;
    @FXML
    private TextField nombre;
    @FXML
    private TextField telefono;
    @FXML
    private TextField correo;
    @FXML
    private TextField direccion;

    @FXML
    private TableView<Proveedor> tableProveedores;
    @FXML
    private TableColumn<Proveedor, Integer> idProveedor;
    @FXML
    private TableColumn<Proveedor, String> nombreProveedor;
    @FXML
    private TableColumn<Proveedor, String> telefonoProveedor;
    @FXML
    private TableColumn<Proveedor, String> correoProveedor;
    @FXML
    private TableColumn<Proveedor, String> direccionProveedor;


    private BaseDatos baseDatos;



    public AgregarProveedoresController() {
        baseDatos = new BaseDatos();
    }

    @FXML
    private void initialize() {
        idProveedor.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreProveedor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        telefonoProveedor.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        correoProveedor.setCellValueFactory(new PropertyValueFactory<>("correo"));
        direccionProveedor.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        cargarProveedores();

        // Add listener to populate fields when a row is selected
        tableProveedores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                llenarCampos(newValue);
            }
        });
    }

    private void llenarCampos(Proveedor proveedor) {
        id.setText(String.valueOf(proveedor.getId()));
        nombre.setText(proveedor.getNombre());
        telefono.setText(proveedor.getTelefono());
        correo.setText(proveedor.getCorreo());
        direccion.setText(proveedor.getDireccion());
    }

    private void cargarProveedores() {
        List<Proveedor> proveedores = baseDatos.obtenerProveedores();
        tableProveedores.getItems().setAll(proveedores);
    }

    @FXML
    private void handleAgregarProveedor() {
        // Verificar que los campos no estén vacíos
        if (id.getText().isEmpty() || nombre.getText().isEmpty() || telefono.getText().isEmpty() ||
                correo.getText().isEmpty() || direccion.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos antes de agregar un proveedor.");
            alert.showAndWait();
            return; // Salir del método si hay campos vacíos
        }

        try {
            int proveedorId = Integer.parseInt(id.getText());
            String proveedorNombre = nombre.getText();
            String proveedorTelefono = telefono.getText();
            String proveedorCorreo = correo.getText();
            String proveedorDireccion = direccion.getText();

            boolean success = baseDatos.agregarProveedor(proveedorId, proveedorNombre, proveedorTelefono, proveedorCorreo, proveedorDireccion);

            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Agregar Proveedor");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Proveedor agregado exitosamente." : "Error al agregar el proveedor.");
            alert.showAndWait();

            if (success) {
                cargarProveedores(); // Actualiza la tabla con los datos más recientes
                limpiarCampos(); // Limpia los campos después de agregar
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Formato");
            alert.setHeaderText(null);
            alert.setContentText("El ID debe ser un número.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleModificarProveedor() {
        // Verificar que los campos no estén vacíos
        if (id.getText().isEmpty() || nombre.getText().isEmpty() || telefono.getText().isEmpty() ||
                correo.getText().isEmpty() || direccion.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos antes de modificar un proveedor.");
            alert.showAndWait();
            return;
        }

        try {
            int proveedorId = Integer.parseInt(id.getText());
            String proveedorNombre = nombre.getText();
            String proveedorTelefono = telefono.getText();
            String proveedorCorreo = correo.getText();
            String proveedorDireccion = direccion.getText();

            boolean success = baseDatos.modificarProveedor(proveedorId, proveedorNombre, proveedorTelefono, proveedorCorreo, proveedorDireccion);

            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Modificar Proveedor");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Proveedor modificado exitosamente." : "Error al modificar el proveedor.");
            alert.showAndWait();

            if (success) {
                cargarProveedores(); // Recargar la tabla con los datos actualizados
                limpiarCampos(); // Limpiar los campos después de modificar
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Formato");
            alert.setHeaderText(null);
            alert.setContentText("El ID debe ser un número.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEliminarProveedor() {
        // Obtener el proveedor seleccionado
        Proveedor proveedorSeleccionado = tableProveedores.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado == null) {
            // Mostrar alerta si no se seleccionó ningún proveedor
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Proveedor no seleccionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un proveedor de la tabla para eliminar.");
            alert.showAndWait();
            return;
        }

        // Confirmar la eliminación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar al proveedor seleccionado?");
        if (confirmacion.showAndWait().orElse(null) != ButtonType.OK) {
            return; // Salir si el usuario cancela
        }

        // Intentar eliminar el proveedor de la base de datos
        boolean success = baseDatos.eliminarProveedor(proveedorSeleccionado.getId());

        // Mostrar mensaje según el resultado
        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle("Eliminar Proveedor");
        alert.setHeaderText(null);
        alert.setContentText(success ? "Proveedor eliminado exitosamente." : "Error al eliminar el proveedor.");
        alert.showAndWait();

        if (success) {
            cargarProveedores(); // Actualizar la tabla
            limpiarCampos(); // Limpiar los campos después de eliminar
        }
    }

    private void limpiarCampos() {
        id.setText("");
        nombre.setText("");
        telefono.setText("");
        correo.setText("");
        direccion.setText("");
    }
}