package controllers;

import BaseDatos.BaseDatos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.List;

public class VentaVistaController {

    @FXML
    private FlowPane flowPaneProductos;

    public void initialize() {
        BaseDatos bd = new BaseDatos();
        List<Producto> productos = bd.obtenerProductos(); // Llama a tu DAO o DB aquí
        for (Producto producto : productos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CartasProductos.fxml"));
                Node card = loader.load();
                FlowPane.setMargin(card, new Insets(10));
                CartaProductoController controller = loader.getController();
                controller.setProducto(producto);

                flowPaneProductos.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
