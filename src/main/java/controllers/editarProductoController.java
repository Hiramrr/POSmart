package controllers;

import BaseDatos.Productos_DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class editarProductoController {
    @FXML private TextField nomEditP;
    @FXML private TextArea descEditP;
    @FXML private TextField cantEditP;
    @FXML private TextField precioCompraEditP;
    @FXML private TextField precioVentaEditP;
    @FXML private ComboBox<String> CatP;
    @FXML private ComboBox<String> UbiP;
    @FXML private ComboBox<String> estadoCb;
    @FXML private Button GuardarEdicionP;
    @FXML private Button CancelEdicionP;

    private Productos_DAO productosDAO = new Productos_DAO();
    private Producto producto;
    AlertPOSmart alerta;
    private byte[] imagenSeleccionada;

    @FXML
    public void initialize() {
        productosDAO.conexion();
        cargarCategorias();
        cargarUbicaciones();
        cargarEstados();
    }

    public void setProducto(Producto producto) {
        this.producto = producto;

        nomEditP.setText(producto.getNombre());
        descEditP.setText(producto.getDescripcion());
        cantEditP.setText(String.valueOf(producto.getCantidad()));
        precioCompraEditP.setText(String.valueOf(producto.getPrecioCompra()));
        precioVentaEditP.setText(String.valueOf(producto.getPrecioVenta()));
        CatP.setValue(producto.getCategoria());
        UbiP.setValue(producto.getUbicacion());
        estadoCb.setValue(producto.getEstado() != null && producto.getEstado() ? "Activo" : "Inactivo");
        this.imagenSeleccionada = producto.getImagen();
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
                precioCompraStr.isEmpty() || precioVentaStr.isEmpty() || categoria == null ||
                ubicacion == null || estadoStr == null) {
            alerta = new AlertPOSmart(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, completa todos los campos.");
            return;
        }

        int cantidad;
        double precioCompra, precioVenta;
        boolean estado = estadoStr.equals("Activo");

        try {
            cantidad = Integer.parseInt(cantidadStr.trim());
            precioCompra = Double.parseDouble(precioCompraStr.trim());
            precioVenta = Double.parseDouble(precioVentaStr.trim());
        } catch (NumberFormatException e) {
            alerta = new AlertPOSmart(Alert.AlertType.ERROR, "Error de formato", "Asegúrate de ingresar solo números válidos en cantidad y precios.");
            return;
        }

        boolean exitoCantidadEstado = productosDAO.actualizarCantidadYDisponibilidad(producto.getId(), cantidad);

        if (!exitoCantidadEstado) {
            alerta = new AlertPOSmart(Alert.AlertType.ERROR, "Error", "No se pudo actualizar cantidad y estado.");
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
                imagenSeleccionada
        );

        boolean exito = productosDAO.agregarProducto(productoActualizado);
        if (exito) {
            alerta = new AlertPOSmart(Alert.AlertType.INFORMATION, "Éxito", "Producto actualizado correctamente.");
            Stage stage = (Stage) GuardarEdicionP.getScene().getWindow();
            stage.close();
        } else {
            alerta = new AlertPOSmart(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el producto.");
        }
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

    private void cargarCategorias() {
        List<String> categorias = productosDAO.obtenerNombresCategorias();
        CatP.getItems().clear();
        CatP.getItems().addAll(categorias);
    }

    private void cargarUbicaciones() {
        List<String> ubicaciones = productosDAO.obtenerNombresUbicaciones();
        UbiP.getItems().clear();
        UbiP.getItems().addAll(ubicaciones);
    }

    private void cargarEstados() {
        estadoCb.getItems().clear();
        estadoCb.getItems().addAll("Activo", "Inactivo");
        estadoCb.setValue("Activo");
    }

    @FXML
    private void handleCancelEdicionP() {
        alerta = new AlertPOSmart(Alert.AlertType.WARNING, "Cancelar", "Cancelando edición del producto");
        Stage stage = (Stage) CancelEdicionP.getScene().getWindow();
        stage.close();
    }
}
