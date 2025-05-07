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

    AlertPOSmart alerta;
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
            mensajeWarning("Campos Vacíos", "Por favor, complete todos los campos antes de agregar un proveedor.");
            return;
        }

        try {
            int proveedorId = Integer.parseInt(id.getText());
            String proveedorNombre = nombre.getText();
            String proveedorTelefono = telefono.getText();
            String proveedorCorreo = correo.getText();
            String proveedorDireccion = direccion.getText();

            boolean success = baseDatos.agregarProveedor(proveedorId, proveedorNombre, proveedorTelefono, proveedorCorreo, proveedorDireccion);

            if (success) {
                cargarProveedores(); // Actualiza la tabla con los datos más recientes
                limpiarCampos(); // Limpia los campos después de agregar
                mensajeBueno("Agregar Proveedor", "Proveedor agregado exitosamente.");
                return;
            }
            mensajeError("Agregar Proveedor", "Error al agregar el proveedor.");
        } catch (NumberFormatException e) {
            mensajeError("Error de Formato", "El ID debe ser un número.");
        }
    }

    @FXML
    private void handleModificarProveedor() {
        // Verificar que los campos no estén vacíos
        if (id.getText().isEmpty() || nombre.getText().isEmpty() || telefono.getText().isEmpty() ||
                correo.getText().isEmpty() || direccion.getText().isEmpty()) {
            mensajeWarning("Campos Vacíos","Por favor, complete todos los campos antes de modificar un proveedor.");
            return;
        }

        try {
            int proveedorId = Integer.parseInt(id.getText());
            String proveedorNombre = nombre.getText();
            String proveedorTelefono = telefono.getText();
            String proveedorCorreo = correo.getText();
            String proveedorDireccion = direccion.getText();

            boolean success = baseDatos.modificarProveedor(proveedorId, proveedorNombre, proveedorTelefono, proveedorCorreo, proveedorDireccion);

            if (success) {
                cargarProveedores(); // Recargar la tabla con los datos actualizados
                limpiarCampos(); // Limpiar los campos después de modificar
                mensajeBueno("Modificar Proveedor", "Proveedor modificado exitosamente.");
                return;
            }
            mensajeError("Modificar Proveedor","Error al modificar el proveedor." );
        } catch (NumberFormatException e) {
            mensajeError("Error de Formato", "El ID debe ser un número.");
        }
    }

    @FXML
    private void handleEliminarProveedor() {
        // Obtener el proveedor seleccionado
        Proveedor proveedorSeleccionado = tableProveedores.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado == null) {
            // Mostrar alerta si no se seleccionó ningún proveedor
            mensajeWarning("Proveedor no seleccionado", "Por favor, seleccione un proveedor de la tabla para eliminar.");
            return;
        }
        // Confirmar la eliminación
        if (!confirmarEliminacion("Confirmar eliminación", "¿Está seguro de que desea eliminar al proveedor seleccionado?")) {
            return; // Salir si el usuario cancela
        }

        // Intentar eliminar el proveedor de la base de datos
        boolean success = baseDatos.eliminarProveedor(proveedorSeleccionado.getId());

        if (success) {
            cargarProveedores(); // Actualizar la tabla
            limpiarCampos(); // Limpiar los campos después de eliminar
            mensajeBueno("Eliminar Proveedor","Proveedor eliminado exitosamente." );
            return;
        }
        mensajeError("Eliminar Proveedor", "Error al eliminar el proveedor.");
    }

    public void mensajeWarning(String titulo, String mensaje){
        alerta = new AlertPOSmart(Alert.AlertType.WARNING, titulo, mensaje);
    }

    public void mensajeError(String titulo, String mensaje){
        alerta = new AlertPOSmart(Alert.AlertType.ERROR, titulo, mensaje);
    }

    public void mensajeBueno(String titulo, String mensaje){
        alerta = new AlertPOSmart(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    private boolean confirmarEliminacion(String titulo, String mensaje) {
        alerta = new AlertPOSmart(Alert.AlertType.CONFIRMATION, titulo, mensaje);
        return alerta.showAndWait().orElse(null) == ButtonType.OK;
    }

    private void limpiarCampos() {
        id.setText("");
        nombre.setText("");
        telefono.setText("");
        correo.setText("");
        direccion.setText("");
    }
}