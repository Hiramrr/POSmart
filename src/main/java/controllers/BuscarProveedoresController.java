package controllers;

import BaseDatos.BaseDatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BuscarProveedoresController {

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

    @FXML
    private Button detallesProveedor;

    @FXML
    private TextField buscar;

    private ObservableList<Proveedor> proveedoresList;

    @FXML
    public void initialize() {
        // Configurar las columnas
        idProveedor.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreProveedor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        telefonoProveedor.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        correoProveedor.setCellValueFactory(new PropertyValueFactory<>("correo"));
        direccionProveedor.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        // Cargar los datos desde la base de datos
        cargarProveedores();

        // Agregar listener al campo de texto para filtrar
        buscar.textProperty().addListener((observable, oldValue, newValue) -> filtrarProveedores(newValue));

        detallesProveedor.setOnAction(event -> mostrarDetallesProveedor());
    }

    private void cargarProveedores() {
        BaseDatos baseDatos = new BaseDatos();
        proveedoresList = FXCollections.observableArrayList(baseDatos.obtenerProveedores());
        tableProveedores.setItems(proveedoresList);
    }

    private void filtrarProveedores(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tableProveedores.setItems(proveedoresList); // Mostrar todos si no hay filtro
        } else {
            ObservableList<Proveedor> filtrados = FXCollections.observableArrayList();
            for (Proveedor proveedor : proveedoresList) {
                if (proveedor.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                    proveedor.getTelefono().toLowerCase().contains(filtro.toLowerCase())) {
                    filtrados.add(proveedor);
                }
            }
            tableProveedores.setItems(filtrados); // Mostrar solo los filtrados
        }
    }

    private void mostrarDetallesProveedor() {
        Proveedor proveedorSeleccionado = tableProveedores.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Proveedor no seleccionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un proveedor de la tabla.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetallesProveedor.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de detalles
            DetallesProveedorController detallesController = loader.getController();
            detallesController.setProveedor(proveedorSeleccionado);

            // Crear y mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Detalles del Proveedor");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}