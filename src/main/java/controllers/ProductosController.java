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



    @FXML
    private TableView<Producto> TProductos;

    @FXML
    private TableColumn<Producto, Integer> IDCol;

    @FXML
    private TableColumn<Producto, String> NomCol;

    @FXML
    private TableColumn<Producto, String> DesCol;

    @FXML
    private TableColumn<Producto, Integer> CantCol;

    @FXML
    private TableColumn<Producto, Double> PrecioCol;

    @FXML
    private Button OpAgregar;

    @FXML
    private Button OpEliminar;

    @FXML
    private Button OpEditar;

    @FXML
    private Button OpListar;

    @FXML
    private Button OpConsultar;

    @FXML
    private ImageView ImgProducto;

    @FXML
    private Text textDia;

    @FXML
    private Text textHora;

    private BaseDatos baseDatos;


    @FXML
    private void initialize() {
        System.out.println("inicializar");
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomCol.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        CantCol.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
        PrecioCol.setCellValueFactory(new PropertyValueFactory<>("PrecioVenta"));
        System.out.println("si paso");

        this.baseDatos = new BaseDatos();
        ObservableList<Producto> productos = baseDatos.obtenerProductos();
        TProductos.setItems(productos);
        System.out.println("si obtuvo los productos");

    }

    @FXML
    private void agregarProducto(ActionEvent event) {
        System.out.println("Clic en 'Agregar Producto'");
        try {
            System.out.println("carga el archivo");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AgregarProductos.fxml"));
            AnchorPane root = loader.load();
            System.out.println("carga el contenido del archivo");

            AgregarProductosController controller = loader.getController();
            controller.setProductosController(this); // 💬 Aquí le pasas la referencia

            System.out.println("crea una nueva ventana");
            Stage stage = new Stage();
            stage.setTitle("Agregar Producto");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println("Error en el archivo");
            e.printStackTrace();
        }
    }

    public void actualizarTabla() {
        System.out.println("entra a actualizar tabla");
        if (baseDatos == null) {
            baseDatos = new BaseDatos(); // Si todavía no tienes una instancia
        }
        System.out.println("si se actualiza");
        ObservableList<Producto> listaProductos = baseDatos.obtenerProductos(); // Este método deberías tenerlo en BaseDatos
        TProductos.setItems(listaProductos);
    }




    @FXML
    private void eliminarProducto(ActionEvent event) {
        Producto productoSeleccionado = TProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un producto para eliminar.");
            return;
        }

        String mensaje = "¿Estás seguro de que deseas eliminar el siguiente producto?\n\n" +
                "ID: " + productoSeleccionado.getId() + "\n" +
                "Nombre: " + productoSeleccionado.getNombre() + "\n" +
                "Descripción: " + productoSeleccionado.getDescripcion() + "\n" +
                "Cantidad en stock: " + productoSeleccionado.getCantidad() + "\n" +
                "Precio de compra: " + productoSeleccionado.getPrecioCompra() + "\n" +
                "Precio de venta: " + productoSeleccionado.getPrecioVenta() + "\n" +
                "Categoría: " + productoSeleccionado.getCategoria() + "\n" +
                "Ubicación: " + productoSeleccionado.getUbicacion();

        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmar Eliminación");
        alertaConfirmacion.setHeaderText("Confirmación requerida");
        alertaConfirmacion.setContentText(mensaje);

        alertaConfirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                if (baseDatos.eliminarProductoDeBaseDeDatos(productoSeleccionado.getId())) {
                    mostrarAlerta("Éxito", "Producto eliminado correctamente.");
                    actualizarTabla();
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el producto.");
                }
            }
        });
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


    @FXML
    private void consultarProducto(ActionEvent event) {
        System.out.println("Consultar Producto");

        Producto productoSeleccionado = TProductos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un producto para consultar.");
            return;
            }

            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mostrarProducto.fxml"));
            Parent root = loader.load();

            mostrarProductoController controller = loader.getController();
            controller.setProducto(productoSeleccionado);

            Stage stage = new Stage();
            stage.setTitle("Detalles del Producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de detalles.");
        }
    }




    // editar producto---------------------------------------------------------------------
    @FXML
    private void handleEditarProducto() {
        Producto productoSeleccionado = TProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/editarProducto.fxml"));
                Parent root = loader.load();

                editarProductoController editarController = loader.getController();
                editarController.setProducto(productoSeleccionado);

                Stage stage = new Stage();
                stage.setTitle("Editar Producto");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana anterior
                stage.showAndWait(); // Espera a que cierres la edición

                actualizarTabla(); // Actualiza la tabla después de editar
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Sin selección", "No se ha seleccionado un producto");
        }
    }



}

