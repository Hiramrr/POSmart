package controllers;

import BaseDatos.Productos_DAO;
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

    @FXML TextField cantidad;

    Productos_DAO baseDatos;

    @FXML
    private void initialize() {
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        CantCol.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        PrecioCol.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        baseDatos = new Productos_DAO();
        baseDatos.conexion();
        cargarProductos();
        cantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cantidad.setText(oldValue);
            }
        });
        TProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cantidad.setText(String.valueOf(newValue.getCantidad()));
            } else {
                cantidad.clear();
            }
        });

        TProductos.setRowFactory(tv -> new TableRow<Producto>() {
            @Override
            protected void updateItem(Producto producto, boolean empty) {
                super.updateItem(producto, empty);

                if (producto == null || empty) {
                    setStyle(""); // Restablece el estilo si está vacío
                } else {
                    int cantidad = producto.getCantidad();
                    if (cantidad == 0) {
                        setStyle("-fx-background-color: #f8d7da;"); // rojo claro
                    } else if (cantidad < 5) {
                        setStyle("-fx-background-color: #fff3cd;"); // amarillo claro
                    } else {
                        setStyle(""); // Sin color si no hay alerta
                    }
                }
            }
        });
    }

    public void cargarProductos(){
        ObservableList<Producto> productos = baseDatos.obtenerProductos();
        if(productos.size() == 0){
            mostrarAlertaError("No hay productos", "No hay productos en el sistema");
            return;
        }
        TProductos.setItems(productos);
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
        confirmacion.setTitle("Confirmar Eliminación Definitiva");
        confirmacion.setHeaderText("¿Estás seguro de eliminar este producto permanentemente?");
        confirmacion.setContentText(
                "ID: " + producto.getId() +
                        "\nNombre: " + producto.getNombre() +
                        "\n\n⚠ Esta acción eliminará también los registros de ventas asociados.\nNo se podrá deshacer."
        );

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                if (baseDatos.eliminarProductoDeBaseDeDatos(producto.getId())) {
                    mostrarAlerta("Éxito", "Producto y registros asociados eliminados correctamente.");
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
            baseDatos = new Productos_DAO();
        }

        cargarProductos();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        AlertPOSmart alerta = new AlertPOSmart(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        AlertPOSmart alerta = new AlertPOSmart(Alert.AlertType.WARNING, titulo, mensaje);
    }

    public void ocultarBotonesParaCajero() {
        OpAgregar.setVisible(false); OpAgregar.setManaged(false);
        OpEliminar.setVisible(false); OpEliminar.setManaged(false);
        OpEditar.setVisible(false); OpEditar.setManaged(false);
        OpListar.setVisible(false); OpListar.setManaged(false);
        OpConsultar.setVisible(false); OpConsultar.setManaged(false);
    }

    @FXML
    void handleAgregarStock(ActionEvent event) {
        Producto producto = TProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un producto para agregar Stock.");
            return;
        }
        if (cantidad.getText() == null || cantidad.getText().trim().isEmpty()) {
            mostrarAlertaError("Llena el campo por favor", "Por favor llena el campo de la nueva cantidad!");
            return;
        }
        int cantidadNueva = Integer.parseInt(cantidad.getText());
        int cantidadAnterior = producto.getCantidad();
        if(cantidadNueva < cantidadAnterior){
            mostrarAlerta("No puedes disminuir la cantidad!", "No puedes disminuir la cantidad del producto!");
            return;
        }
        if(!baseDatos.agregarStock(producto.getId(), cantidadNueva)){
            mostrarAlertaError("Introduce algo valido!", "por favor introduce algo valido en el campo de cantidad");
            return;
        }
        actualizarTabla();
        mostrarAlerta("Se actualizo el stock con exito", "Se actualizo el stock del producto " + producto.getNombre() + " con exito!");
    }

    @FXML
    void handleSumar(ActionEvent event) {
        int cantidadActual = Integer.parseInt(cantidad.getText());
        int cantidadNueva = cantidadActual + 1;
        cantidad.setText(String.valueOf(cantidadNueva));
    }
}
