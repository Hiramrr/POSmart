package controllers;

import BaseDatos.BaseDatos;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductosController {

    @FXML private TableView<Producto> TProductos;
    @FXML private TableColumn<Producto, Integer> IDCol;
    @FXML private TableColumn<Producto, String> NomCol;
    @FXML private TableColumn<Producto, String> DesCol;
    @FXML private TableColumn<Producto, Integer> CantCol;
    @FXML private TableColumn<Producto, Double> PrecioCol;

    @FXML private Button OpAgregar;
    @FXML private Button OpEliminar;
    @FXML private Button OpEditar;
    @FXML private Button OpListar;
    @FXML private Button OpConsultar;

    @FXML private ImageView ImgProducto;
    @FXML private Text textDia;
    @FXML private Text textHora;

    private BaseDatos baseDatos;

    @FXML
    private void initialize() {
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        CantCol.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        PrecioCol.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        baseDatos = new BaseDatos();
        TProductos.setItems(baseDatos.obtenerProductosActivos());
    }

    @FXML
    private void agregarProducto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AgregarProductos.fxml"));
            AnchorPane root = loader.load();

            AgregarProductosController controller = loader.getController();
            controller.setProductosController(this);

            Stage stage = new Stage();
            stage.setTitle("Agregar Producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            actualizarTabla();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la ventana de agregar producto.");
        }
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {
        Producto producto = TProductos.getSelectionModel().getSelectedItem();
        if (producto == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un producto para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar este producto?");
        confirmacion.setContentText("ID: " + producto.getId() + "\nNombre: " + producto.getNombre());

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                if (baseDatos.eliminarProductoDeBaseDeDatos(producto.getId())) {
                    mostrarAlerta("Éxito", "Producto eliminado correctamente.");
                    actualizarTabla();
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el producto.");
                }
            }
        });
    }

    @FXML
    private void handleEditarProducto() {
        Producto producto = TProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un producto para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editarProducto.fxml"));
            Parent root = loader.load();

            editarProductoController controller = loader.getController();
            controller.setProducto(producto);

            Stage stage = new Stage();
            stage.setTitle("Editar Producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            actualizarTabla();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la ventana de edición.");
        }
    }

    @FXML
    private void consultarProducto(ActionEvent event) {
        Producto producto = TProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un producto para consultar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mostrarProducto.fxml"));
            Parent root = loader.load();

            mostrarProductoController controller = loader.getController();
            controller.setProducto(producto);

            Stage stage = new Stage();
            stage.setTitle("Detalles del Producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la ventana de detalles.");
        }
    }

    public void actualizarTabla() {
        if (baseDatos == null) {
            baseDatos = new BaseDatos();
        }

        TProductos.setItems(baseDatos.obtenerProductosActivos());

    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void ocultarBotonesParaCajero() {
        OpAgregar.setVisible(false); OpAgregar.setManaged(false);
        OpEliminar.setVisible(false); OpEliminar.setManaged(false);
        OpEditar.setVisible(false); OpEditar.setManaged(false);
        OpListar.setVisible(false); OpListar.setManaged(false);
        OpConsultar.setVisible(false); OpConsultar.setManaged(false);
    }
}
