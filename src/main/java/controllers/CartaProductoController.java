package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class CartaProductoController {

    @FXML private Label cartaNombre;
    @FXML private Label cartaPrecio;
    @FXML private ImageView imagen;

    public void setProducto(Producto producto) {
        cartaNombre.setText(producto.getNombre());
        cartaPrecio.setText("$" + producto.getPrecioVenta());

        if (producto.getImagen() != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(producto.getImagen());
            imagen.setImage(new Image(bis));
        } else {
            imagen.setImage(null); // o una imagen por defecto si quieres
        }
    }

}
