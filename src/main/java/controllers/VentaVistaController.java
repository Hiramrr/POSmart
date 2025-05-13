package controllers;
import BaseDatos.BaseDatos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;
public class VentaVistaController {

    @FXML
    private FlowPane flowPaneProductos;
    @FXML
    private VBox vboxVenta;
    @FXML
    private Label totalLabel;

    private double total = 0.0;

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

                // Detectar doble clic
                card.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        agregarProductoACesta(producto);
                    }
                });

                flowPaneProductos.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void agregarProductoACesta(Producto producto) {
        for (Node node : vboxVenta.getChildren()) {
            if (node instanceof HBox hbox) {
                if (producto.getNombre().equals(hbox.getUserData())) {
                    Label cantidadLabel = (Label) hbox.lookup(".cart-qty");
                    Label precioLabel = (Label) hbox.lookup(".cart-price");

                    int cantidad = Integer.parseInt(cantidadLabel.getText()) + 1;
                    cantidadLabel.setText(String.valueOf(cantidad));

                    double precioTotalProducto = cantidad * producto.getPrecioVenta();
                    precioLabel.setText("$" + String.format("%.2f", precioTotalProducto));

                    total += producto.getPrecioVenta();
                    totalLabel.setText("$" + String.format("%.2f", total));
                    return;
                }
            }
        }

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setUserData(producto.getNombre());
        hbox.getStyleClass().add("cart-row");

        Button btnRestar = new Button("➖");
        btnRestar.getStyleClass().add("button");

        Label cantidadLabel = new Label("1");
        cantidadLabel.getStyleClass().add("cart-qty");

        Label nombreLabel = new Label(producto.getNombre());
        nombreLabel.getStyleClass().add("cart-name");

        // si los nombres de los productos son mas cortos  se empuja el precio a la derecha
        Region spacer = new Region();
        spacer.getStyleClass().add("spacer");
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label precioLabel = new Label("$" + String.format("%.2f", producto.getPrecioVenta()));
        precioLabel.getStyleClass().add("cart-price");

        btnRestar.setOnAction(e -> {
            int cantidad = Integer.parseInt(cantidadLabel.getText()) - 1;
            total -= producto.getPrecioVenta();

            if (cantidad <= 0) {
                vboxVenta.getChildren().remove(hbox);
            } else {
                cantidadLabel.setText(String.valueOf(cantidad));
                double precioTotalProducto = cantidad * producto.getPrecioVenta();
                precioLabel.setText("$" + String.format("%.2f", precioTotalProducto));
            }

            totalLabel.setText("$" + String.format("%.2f", total));
        });

        hbox.getChildren().addAll(btnRestar, cantidadLabel, nombreLabel, spacer, precioLabel);
        vboxVenta.getChildren().add(hbox);

        total += producto.getPrecioVenta();
        totalLabel.setText("$" + String.format("%.2f", total));
    }



}
