package controllers;

import BaseDatos.BaseDatos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import controllers.Producto;

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

    private BaseDatos baseController = new BaseDatos();
    private Producto producto;
    private static Connection con;

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
    }

    // Método para recibir el producto seleccionado
    public void setProducto(Producto producto) {
        this.producto = producto;

        // Cargar datos en los campos
        nomEditP.setText(producto.getNombre());
        descEditP.setText(producto.getDescripcion());
        cantEditP.setText(String.valueOf(producto.getCantidad()));
        precioCompraEditP.setText(String.valueOf(producto.getPrecioCompra()));
        precioVentaEditP.setText(String.valueOf(producto.getPrecioVenta()));
         // ID de categoría
        //ubiEditP.setText(String.valueOf(producto.getUbicacion())); // ID de ubicación

        int idcat = Integer.parseInt(producto.getCategoria());
        Categoria c = baseController.obtenerCategoriaPorId(idcat);
        CatP.setValue(String.valueOf(c.getNombre()));
//        CatP.setValue(producto.getCategoria());
        int idubi = Integer.parseInt(producto.getUbicacion());
        Ubicacion u = baseController.obtenerUbicacionPorId(idubi);
        UbiP.setValue(String.valueOf(u.getNombre()));
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
        // Obtener valores de los campos
        String nombre = nomEditP.getText();
        String descripcion = descEditP.getText();
        String cantidadStr = cantEditP.getText();
        String precioCompraStr = precioCompraEditP.getText();
        String precioVentaStr = precioVentaEditP.getText();
        String categoria = CatP.getValue();
        String ubicacion = UbiP.getValue();

        // Validar campos vacíos
        if (nombre.isEmpty() || descripcion.isEmpty() || cantidadStr.isEmpty() ||
                precioCompraStr.isEmpty() || precioVentaStr.isEmpty() || categoria.isEmpty() || ubicacion.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, completa todos los campos.");
            return;
        }

        int cantidad;
        double precioCompra, precioVenta;

        // Validar formato de cantidad
        try {
            cantidad = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato inválido", "El campo 'Cantidad' debe ser un número entero válido.");
            return;
        }

        // Validar formato de precio de compra
        try {
            precioCompra = Double.parseDouble(precioCompraStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato inválido", "El campo 'Precio de Compra' debe ser un número decimal válido.");
            return;
        }

        // Validar formato de precio de venta
        try {
            precioVenta = Double.parseDouble(precioVentaStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato inválido", "El campo 'Precio de Venta' debe ser un número decimal válido.");
            return;
        }

        // Crear el producto actualizado
        Producto productoActualizado = new Producto(
                producto.getId(),
                nombre,
                descripcion,
                cantidad,
                precioCompra,
                precioVenta,
                categoria,
                ubicacion
        );

        // Actualizar en la base de datos
        if (baseController.actualizarProductoEnBaseDeDatos(productoActualizado)) {
            mostrarAlerta("Éxito", "Producto actualizado con éxito.");
            Stage stage = (Stage) GuardarEdicionP.getScene().getWindow();
            stage.close();
        } else {
            mostrarAlerta("Error", "Hubo un error al actualizar el producto.");
        }
    }





    public void handleCancelEdicionP() {
        mostrarAlerta("Cancelar","Cancelando edicion del producto");
        Stage stage = (Stage) CancelEdicionP.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

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

}

