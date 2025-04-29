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

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class editarProductoController {
    @FXML private TextField nomEditP;
    @FXML private TextArea descEditP;
    @FXML private TextField cantEditP;
    @FXML private TextField precioEditP;
    @FXML private TextField catEditP;
    @FXML private TextField ubiEditP;
    @FXML private Button GuardarEdicionP;
    @FXML private Button CancelarEdicionP;

    private BaseDatos baseController = new BaseDatos();
    private Producto producto;

    // Método para recibir el producto seleccionado
    public void setProducto(Producto producto) {
        this.producto = producto;

        // Cargar datos en los campos
        nomEditP.setText(producto.getNombre());
        descEditP.setText(producto.getDescripcion());
        cantEditP.setText(String.valueOf(producto.getCantidad()));
        precioEditP.setText(String.valueOf(producto.getPrecio()));
        catEditP.setText(producto.getCategoria());
        ubiEditP.setText(producto.getUbicacion());
    }

    @FXML
    private void guardarProductoEditado() {
        // Actualizar los datos del producto
        producto.setNombre(nomEditP.getText());
        producto.setDescripcion(descEditP.getText());
        producto.setCantidad(Integer.parseInt(cantEditP.getText()));
        producto.setPrecio(Double.parseDouble(precioEditP.getText()));
        producto.setCategoria(catEditP.getText());
        producto.setUbicacion(ubiEditP.getText());

        // Aquí llamas al método que actualiza el producto en la base de datos
        // Por ejemplo:
        baseController.actualizarProductoEnBaseDeDatos(producto);

        // Cerrar la ventana de edición
        Stage stage = (Stage) nomEditP.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
        System.out.println("Obtener los valores editados");
        String nombre = nomEditP.getText();
        String descripcion = descEditP.getText();
        double precio = Double.parseDouble(precioEditP.getText());
        int cantidad = Integer.parseInt(cantEditP.getText());
        String categoria = catEditP.getText();
        String ubicacion = ubiEditP.getText();

        System.out.println("Crear el producto actualizado");
        Producto productoActualizado = new Producto(
                producto.getId(),  // ID del producto original (no se edita)
                nombre,
                descripcion,
                cantidad,
                precio,
                categoria,
                ubicacion
        );

        System.out.println("Actualizar en la base de datos");
        if (baseController.actualizarProductoEnBaseDeDatos(productoActualizado)) {
            System.out.println("Producto actualizado con éxito.");
            // Cerrar la ventana de edición (opcional)
            Stage stage = (Stage) GuardarEdicionP.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Hubo un error al actualizar el producto.");
        }
    }




}
