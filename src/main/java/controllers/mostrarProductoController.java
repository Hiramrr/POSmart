package controllers;

import BaseDatos.BaseDatos;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import controllers.Producto;

public class mostrarProductoController {

    @FXML
    private Label lblIdProducto;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblCantidad;
    @FXML
    private Label lblPrecioCompra;
    @FXML
    private Label lblPrecioVenta;
    @FXML
    private Label lblCategoria;
    @FXML
    private Label lblUbicacion;

    private Producto producto;
    private BaseDatos baseController = new BaseDatos();

    public void setProducto(Producto producto) {
        this.producto = producto;

        lblIdProducto.setText("ID: " + producto.getId());
        lblNombre.setText("Nombre: " + producto.getNombre());
        lblDescripcion.setText("Descripción: " + producto.getDescripcion());
        lblCantidad.setText("Cantidad en stock: " + producto.getCantidad());
        lblPrecioCompra.setText("Precio de compra: " + producto.getPrecioCompra());
        lblPrecioVenta.setText("Precio de venta: " + producto.getPrecioVenta());
         lblCategoria.setText("Categoría: " + producto.getCategoria());
         lblUbicacion.setText("Ubicación: " + producto.getUbicacion());

       /* int idcat = Integer.parseInt(producto.getCategoria());
        Categoria c = baseController.obtenerCategoriaPorId(idcat);
        lblCategoria.setText("Categoría: " + c.getNombre());

        int idubi = Integer.parseInt(producto.getUbicacion());
        Ubicacion u = baseController.obtenerUbicacionPorId(idubi);
        lblUbicacion.setText("Ubicación: " + u.getNombre());

        */

    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        stage.close();
    }
}