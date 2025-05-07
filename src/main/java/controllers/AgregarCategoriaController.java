package controllers;

import BaseDatos.BaseDatos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AgregarCategoriaController {

    @FXML
    private TextField id;
    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;

    @FXML
    private TableView<Categoria> tablaCategoria;
    @FXML
    private TableColumn<Categoria, Integer> idCategoria;
    @FXML
    private TableColumn<Categoria, String> nombreCategoria;
    @FXML
    private TableColumn<Categoria, String> descripcionCategoria;

    @FXML
    private Button buttonAgregarCategoria;

    @FXML
    private Button buttonModificarCategoria;

    @FXML
    private Button buttonEliminarCategoria;

    private BaseDatos baseDatos;

    AlertPOSmart alerta;


    public AgregarCategoriaController() {
        baseDatos = new BaseDatos();
    }

    @FXML
    private void initialize() {
        idCategoria.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcion"));


        cargarCategoria();

        tablaCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                llenarCampos(newValue);
            }
        });
    }

    private void llenarCampos(Categoria categoria) {
        id.setText(String.valueOf(categoria.getId()));
        nombre.setText(categoria.getNombre());
        descripcion.setText(categoria.getDescripcion());
    }

    private void cargarCategoria() {
        List<Categoria> categorias = baseDatos.obtenerCategorias();
        tablaCategoria.getItems().setAll(categorias);
    }

    @FXML
    private void handleAgregarCategoria() {
        if (id.getText().isEmpty() || nombre.getText().isEmpty() || descripcion.getText().isEmpty()){
            mensajeWarning("Campos vacios", "\"Por favor, complete todos los campos antes de agregar una categoria");
        }
        try {
            int categoriaID = Integer.parseInt(id.getText());
            String categoriaNombre = nombre.getText();
            String ubicacionCategoria = descripcion.getText();
            boolean success = baseDatos.agregarCategoria(categoriaID, categoriaNombre, ubicacionCategoria);
            if(success){
                cargarCategoria();
                limpiarCampos();
                mensajeBueno("Se agrego una categoria", "Se agrego la categoria con exito!");
                return;
            }
            mensajeError("Se intento agregar una categoria : Error","Hubo un error al intentar agregar una categoria, por favor contacta a un administrador");
        } catch (Exception e){
            mensajeError("El ID debe de ser un numero", "Por favor introduce un numero valido en la id");
        }
    }

    @FXML
    private void handleModificarCategoria() {
        if (id.getText().isEmpty() || nombre.getText().isEmpty() || descripcion.getText().isEmpty()){
            mensajeWarning("Campos vacios", "\"Por favor, complete todos los campos antes de modificar una categoria");
        }
        try {
            int categoriaID = Integer.parseInt(id.getText());
            String categoriaNombre = nombre.getText();
            String categoriaDescripcion = descripcion.getText();
            boolean success = baseDatos.modificarCategoria(categoriaID, categoriaNombre, categoriaDescripcion);
            if(success){
                cargarCategoria();
                limpiarCampos();
                mensajeBueno("Se modifico una categoria", "Se modifico la categoria con exito!");
                return;
            }
            mensajeError("Se intento modificar una categoria : Error","Hubo un error al intentar modificar una categoria, por favor contacta a un administrador");
        } catch (Exception e){
            mensajeError("El ID debe de ser un numero", "Por favor introduce un numero valido en la id");
        }
    }

    @FXML
    private void handleEliminarCategoria() {
        Categoria categoriaSeleccionada = tablaCategoria.getSelectionModel().getSelectedItem();

        if (categoriaSeleccionada == null) {
            mensajeWarning("Selecciona una categoria", "Por favor selecciona una categoria");
        }

        if (!confirmarEliminacion("Confirmar eliminación", "¿Está seguro de que desea eliminar la categoria seleccionado?")) {
            return;
        }

        boolean success = baseDatos.eliminarCategoria(categoriaSeleccionada.getId());

        if (success) {
            cargarCategoria();
            limpiarCampos();
            mensajeBueno("Se elimino con exito la categoria", "Se elimino con exito la categoria!");
            return;
        }
        mensajeError("Error al eliminar la categoria", "Hubo un erro al intentar eliminar la categoria, por favor contacta a un administrador");
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