package controllers;

import BaseDatos.BaseDatos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AgregarUbicacionController {

    @FXML
    private TextField id;
    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;

    @FXML
    private TableView<Ubicacion> tableUbicacion;
    @FXML
    private TableColumn<Ubicacion, Integer> idUbicacion;
    @FXML
    private TableColumn<Ubicacion, String> nombreUbicacion;
    @FXML
    private TableColumn<Ubicacion, String> descripcionUbicacion;

    @FXML
    private Button buttonAgregarUbicacion;

    @FXML
    private Button buttonModificarUbicacion;

    @FXML
    private Button buttonEliminarUbicacion;

    private BaseDatos baseDatos;

    AlertPOSmart alerta;


    public AgregarUbicacionController() {
        baseDatos = new BaseDatos();
    }

    @FXML
    private void initialize() {
        idUbicacion.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreUbicacion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionUbicacion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));


        cargarUbicacion();

        tableUbicacion.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                llenarCampos(newValue);
            }
        });
    }

    private void llenarCampos(Ubicacion ubicacion) {
        id.setText(String.valueOf(ubicacion.getId()));
        nombre.setText(ubicacion.getNombre());
        descripcion.setText(ubicacion.getDescripcion());
    }

    private void cargarUbicacion() {
        List<Ubicacion> ubicaciones = baseDatos.obtenerUbicaciones();
        tableUbicacion.getItems().setAll(ubicaciones);
    }

    @FXML
    private void handleAgregarUbicacion() {
        if (id.getText().isEmpty() || nombre.getText().isEmpty() || descripcion.getText().isEmpty()){
            mensajeWarning("Campos vacios", "\"Por favor, complete todos los campos antes de agregar una ubicacion");
        }
        try {
            int ubicacionID = Integer.parseInt(id.getText());
            String ubicacionNombre = nombre.getText();
            String ubicacionDescripcion = descripcion.getText();
            boolean success = baseDatos.agregarUbicacion(ubicacionID,ubicacionNombre,ubicacionDescripcion);
            if(success){
                cargarUbicacion();
                limpiarCampos();
                mensajeBueno("Se agrego una ubicacion", "Se agrego la ubicacion con exito!");
                return;
            }
            mensajeError("Se intento agregar una ubicacion : Error","Hubo un error al intentar agregar una ubicacion, por favor contacta a un administrador");
        } catch (Exception e){
            mensajeError("El ID debe de ser un numero", "Por favor introduce un numero valido en la id");
        }
    }

    @FXML
    private void handleModificarUbicacion() {
        if (id.getText().isEmpty() || nombre.getText().isEmpty() || descripcion.getText().isEmpty()){
            mensajeWarning("Campos vacios", "\"Por favor, complete todos los campos antes de modificar una ubicacion");
        }
        try {
            int ubicacionID = Integer.parseInt(id.getText());
            String ubicacionNombre = nombre.getText();
            String ubicacionDescripcion = descripcion.getText();
            boolean success = baseDatos.modificarUbicacion(ubicacionID,ubicacionNombre,ubicacionDescripcion);
            if(success){
                cargarUbicacion();
                limpiarCampos();
                mensajeBueno("Se modifico una ubicacion", "Se modifico la ubicacion con exito!");
                return;
            }
            mensajeError("Se intento modificar una ubicacion : Error","Hubo un error al intentar modificar una ubicacion, por favor contacta a un administrador");
        } catch (Exception e){
            mensajeError("El ID debe de ser un numero", "Por favor introduce un numero valido en la id");
        }
    }

    @FXML
    private void handleEliminarUbicacion() {
        Ubicacion ubicacionSeleccionada = tableUbicacion.getSelectionModel().getSelectedItem();

        if (ubicacionSeleccionada == null) {
            mensajeWarning("Selecciona una ubicacion", "Por favor selecciona una ubicacion");
        }

        if (!confirmarEliminacion("Confirmar eliminación", "¿Está seguro de que desea eliminar la ubicacion seleccionado?")) {
            return;
        }

        boolean success = baseDatos.eliminarUbicacion(ubicacionSeleccionada.getId());

        if (success) {
            cargarUbicacion();
            limpiarCampos();
            mensajeBueno("Se elimino con exito la ubicacion", "Se elimino con exito la ubicacion!");
            return;
        }
        mensajeError("Error al eliminar la ubicacion", "Hubo un erro al intentar eliminar la ubicacion, por favor contacta a un administrador");
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

    public void limpiarCampos(){
        id.setText("");
        nombre.setText("");
        descripcion.setText("");
    }
}