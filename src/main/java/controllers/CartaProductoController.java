package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CartaProductoController {

    @FXML private Label cartaNombre;
    @FXML private Label cartaPrecio;
    @FXML private ImageView imagen;

    public void setProducto(Producto producto) {
        cartaNombre.setText(producto.getNombre());
        cartaPrecio.setText("$" + producto.getPrecioVenta());
        // imagen.setImage(new Image(producto.getUrlImagen())); // opcional
    }

}
