package controllers;

import BaseDatos.BaseDatos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import controllers.Producto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class editarProductoController {
    @FXML private TextField nomEditP;
    @FXML private TextArea descEditP;
    @FXML private TextField cantEditP;
    @FXML private TextField precioCompraEditP;
    @FXML private TextField precioVentaEditP;
    @FXML private TextField catEditP;
    @FXML private TextField ubiEditP;
    @FXML private Button GuardarEdicionP;
    @FXML private Button CancelEdicionP;
    @FXML private ComboBox<String> CatP;
    @FXML private ComboBox<String> UbiP;
    @FXML private ComboBox<String> estadoCb;  // Asegúrate que el fx:id en el FXML sea "estadoCb" o actualízalo aquí


    private BaseDatos baseController = new BaseDatos();
    private Producto producto;
    private static Connection con;
    AlertPOSmart alerta;
    private byte[] imagenSeleccionada; // Guardará la imagen actual o la nueva seleccionada


    public editarProductoController() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/POSMart", "Hiram", "coco123");
            System.out.println("Si ves esto es que se conecto la base");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void initialize() {
        cargarCategorias();
        cargarUbicaciones();
        cargarEstados();
    }

    // Método para recibir el producto seleccionado
    public void setProducto(Producto producto) {
        this.producto = producto;

        nomEditP.setText(producto.getNombre());
        descEditP.setText(producto.getDescripcion());
        cantEditP.setText(String.valueOf(producto.getCantidad()));
        precioCompraEditP.setText(String.valueOf(producto.getPrecioCompra()));
        precioVentaEditP.setText(String.valueOf(producto.getPrecioVenta()));

        // Aquí asumimos que producto.getCategoria() ya es el nombre
        CatP.setValue(producto.getCategoria());

        // Igual para ubicación
        UbiP.setValue(producto.getUbicacion());

        Boolean estado = producto.getEstado();
        if (estado != null) {
            estadoCb.setValue(estado ? "Activo" : "Inactivo");
        } else {
            estadoCb.setValue("Inactivo"); // o "Activo", como prefieras
        }

        // Imagen y demás...
        this.imagenSeleccionada = producto.getImagen();
    }



    @FXML
    private void guardarProductoEditado() {
        // Actualizar los datos del producto
        producto.setNombre(nomEditP.getText());
        producto.setDescripcion(descEditP.getText());
        producto.setCantidad(Integer.parseInt(cantEditP.getText()));
        producto.setPrecioCompra(Integer.parseInt(precioCompraEditP.getText()));
        producto.setPrecioVenta(Integer.parseInt(precioVentaEditP.getText()));
        producto.setCategoria(catEditP.getText()); // Usar ID de categoría
        producto.setUbicacion(ubiEditP.getText()); // Usar ID de ubicación

        baseController.actualizarProductoEnBaseDeDatos(producto);

        // Cerrar la ventana de edición
        Stage stage = (Stage) nomEditP.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
        String nombre = nomEditP.getText();
        String descripcion = descEditP.getText();
        String cantidadStr = cantEditP.getText();
        String precioCompraStr = precioCompraEditP.getText();
        String precioVentaStr = precioVentaEditP.getText();
        String categoria = CatP.getValue();
        String ubicacion = UbiP.getValue();
        String estadoStr = estadoCb.getValue();

        if (nombre.isEmpty() || descripcion.isEmpty() || cantidadStr.isEmpty() ||
                precioCompraStr.isEmpty() || precioVentaStr.isEmpty() || categoria.isEmpty() ||
                ubicacion.isEmpty() || estadoStr.isEmpty()) {
            alerta = new AlertPOSmart(Alert.AlertType.WARNING,"Campos vacíos", "Por favor, completa todos los campos.");
            return;
        }

        int cantidad;
        double precioCompra, precioVenta;
        boolean estado = estadoStr.equals("Activo");

        try {
            cantidad = Integer.parseInt(cantidadStr);
            precioCompra = Double.parseDouble(precioCompraStr);
            precioVenta = Double.parseDouble(precioVentaStr);
        } catch (NumberFormatException e) {
            alerta = new AlertPOSmart(Alert.AlertType.ERROR,"Error", "Verifica los campos numéricos.");
            return;
        }

        Producto productoActualizado = new Producto(
                producto.getId(),
                nombre,
                descripcion,
                cantidad,
                precioCompra,
                precioVenta,
                categoria,
                ubicacion,
                estado,
                imagenSeleccionada // usa la imagen nueva o la antigua si no cambió
        );

        if (baseController.actualizarProductoEnBaseDeDatos(productoActualizado)) {
            alerta = new AlertPOSmart(Alert.AlertType.INFORMATION,"Éxito", "Producto actualizado con éxito.");
            Stage stage = (Stage) GuardarEdicionP.getScene().getWindow();
            stage.close();
        } else {
            alerta = new AlertPOSmart(Alert.AlertType.ERROR,"Error", "Hubo un error al actualizar el producto.");
        }
    }

    public void handleCancelEdicionP() {
        alerta = new AlertPOSmart(Alert.AlertType.WARNING,"Cancelar","Cancelando edicion del producto");
        Stage stage = (Stage) CancelEdicionP.getScene().getWindow();
        stage.close();
    }

//    private void mostrarAlerta(String titulo, String mensaje) {
//        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
//        alerta.setTitle(titulo);
//        alerta.setHeaderText(null);
//        alerta.setContentText(mensaje);
//        alerta.showAndWait();
//    }

    private void cargarCategorias() {
        List<String> categorias = baseController.obtenerNombresCategorias();
        CatP.getItems().clear();
        CatP.getItems().addAll(categorias);
    }

    private void cargarUbicaciones() {
        List<String> ubicaciones = baseController.obtenerNombresUbicaciones();
        UbiP.getItems().clear();
        UbiP.getItems().addAll(ubicaciones);
    }

    private void cargarEstados() {
        estadoCb.getItems().clear();
        estadoCb.getItems().addAll("Activo", "Inactivo");
        estadoCb.setValue("Activo");
    }

    @FXML
    private void handleAgregarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) GuardarEdicionP.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                imagenSeleccionada = fis.readAllBytes();
                alerta = new AlertPOSmart(Alert.AlertType.INFORMATION, "Imagen cargada", "Imagen seleccionada correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
                alerta = new AlertPOSmart(Alert.AlertType.ERROR, "Error", "No se pudo cargar la imagen.");
            }
        }
    }
}

