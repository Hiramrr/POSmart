package controllers;
import BaseDatos.BaseDatos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import BaseDatos.Compras_DAO;


public class VentaVistaController {
    @FXML
    private FlowPane flowPaneProductos;
    @FXML
    private VBox vboxVenta;
    @FXML
    private Label totalLabel;

    private double total = 0.0;

    @FXML
    private TextField busquedaField;

    private List<Producto> todosLosProductos;
    private BaseDatos bd = new BaseDatos();


    public void initialize() {
        todosLosProductos = bd.obtenerProductos();
        mostrarProductos(todosLosProductos);
        busquedaField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filtro = newValue.trim().toLowerCase(); // Elimina espacios extras y pasa a minúsculas

            List<Producto> filtrados = todosLosProductos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(filtro) ||
                            p.getCategoria().toLowerCase().contains(filtro) ||
                            p.getDescripcion().toLowerCase().contains(filtro) ||
                            p.getUbicacion().toLowerCase().contains(filtro))
                    .toList();

            mostrarProductos(filtrados);
        });
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

    private void mostrarProductos(List<Producto> productos) {
        flowPaneProductos.getChildren().clear();

        for (Producto producto : productos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CartasProductos.fxml"));
                Node card = loader.load();
                FlowPane.setMargin(card, new Insets(10));
                CartaProductoController controller = loader.getController();
                controller.setProducto(producto);

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

    public void finalizarCompra() {
        if (AlertaUtil.confirmar("Confirmar Compra", "¿Estás seguro de finalizar la compra?")) {
            // Crear una nueva instancia de CompraDAO
            Compras_DAO comprasDAO = new Compras_DAO();

            // Obtener el nombre de usuario (esto es solo un ejemplo, deberías tener el nombre del usuario logeado)
            String username = "nombreDeUsuario";  // Asegúrate de obtener el usuario logeado correctamente
            int idUsuario = bd.obtenerIdUsuario(username);

            // Si no se pudo obtener el id del usuario
            if (idUsuario == -1) {
                AlertaUtil.mostrarError("Error", "No se pudo obtener el id del usuario.");
                return;
            }

            // Obtener el id del proveedor (puedes obtenerlo de alguna lógica, por ejemplo, por nombre)
            String nombreProveedor = "Proveedor A";  // Aquí deberías obtener el proveedor seleccionado
            int idProveedor = bd.obtenerIdProveedor(nombreProveedor);

            // Si no se pudo obtener el id del proveedor
            if (idProveedor == -1) {
                AlertaUtil.mostrarError("Error", "No se pudo obtener el id del proveedor.");
                return;
            }

            // Obtener la fecha actual y el total de la compra
            String fechaActual = LocalDate.now().toString(); // Suponiendo que usas LocalDate para obtener la fecha
            double totalCompra = total; // Usar la variable total de la compra (ya es un double)

            // Agregar la compra a la base de datos
            boolean compraExitosa = comprasDAO.agregarCompra(fechaActual, totalCompra, idProveedor, idUsuario);
            if (!compraExitosa) {
                AlertaUtil.mostrarError("Error", "No se pudo registrar la compra.");
                return;
            }

            // Obtener el id de la compra insertada
            int idCompra = comprasDAO.obtenerUltimoIdCompra(); // Debes crear un método para obtener el último id insertado.

            // Insertar los detalles de la compra
            for (Node node : vboxVenta.getChildren()) {
                if (node instanceof HBox hbox) {
                    String nombreProducto = (String) hbox.getUserData();
                    Label cantidadLabel = (Label) hbox.lookup(".cart-qty");
                    int cantidadVendida = Integer.parseInt(cantidadLabel.getText());

                    Producto productoOriginal = todosLosProductos.stream()
                            .filter(p -> p.getNombre().equals(nombreProducto))
                            .findFirst()
                            .orElse(null);

                    if (productoOriginal != null) {
                        int nuevoStock = productoOriginal.getCantidad() - cantidadVendida;
                        productoOriginal.setCantidad(nuevoStock);

                        // Actualizar el stock del producto en la base de datos
                        if (nuevoStock <= 0) {
                            bd.eliminarProductoDeBaseDeDatos(productoOriginal.getId());
                        } else {
                            bd.actualizarProductoEnBaseDeDatos(productoOriginal);
                        }

                        // Agregar el detalle de la compra
                        double montoFinal = cantidadVendida * productoOriginal.getPrecioVenta();
                        boolean detalleExitoso = comprasDAO.agregarDetalleCompra(productoOriginal.getId(), idCompra, cantidadVendida, montoFinal);
                        if (!detalleExitoso) {
                            AlertaUtil.mostrarError("Error", "No se pudo registrar el detalle de la compra.");
                            return;
                        }
                    }
                }
            }

            // Limpiar la vista después de finalizar la compra
            vboxVenta.getChildren().clear();
            total = 0.0;
            totalLabel.setText("$0.00");
            todosLosProductos = bd.obtenerProductos();
            mostrarProductos(todosLosProductos);

            // Mostrar mensaje de éxito
            AlertaUtil.mostrarInfo("Compra Finalizada", "La compra se realizó con éxito.");
        }
    }



}
