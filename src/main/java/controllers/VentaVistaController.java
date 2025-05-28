package controllers;

import BaseDatos.*;
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
import java.util.stream.Collectors;

public class VentaVistaController {
    @FXML private FlowPane flowPaneProductos;
    @FXML private VBox vboxVenta;
    @FXML private Label totalLabel;
    @FXML private TextField busquedaField;

    private double total = 0.0;
    private List<Producto> todosLosProductos;
    private BaseDatos bd = new BaseDatos();

    public void initialize() {
        todosLosProductos = bd.obtenerProductos();
        cargarProductosDisponibles();
        busquedaField.textProperty().addListener((obs, oldVal, newVal) -> buscarProducto(newVal));
    }

    private void buscarProducto(String busqueda) {
        String filtro = busqueda.trim().toLowerCase();
        List<Producto> filtrados = todosLosProductos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(filtro) ||
                        p.getCategoria().toLowerCase().contains(filtro) ||
                        p.getDescripcion().toLowerCase().contains(filtro) ||
                        p.getUbicacion().toLowerCase().contains(filtro))
                .toList();

        if (filtrados.isEmpty()) {
            AlertaUtil.mostrarInfo("Sin resultados", "No se encontraron productos.");
        }

        mostrarProductos(filtrados);
    }

    private void agregarProductoACesta(Producto producto) {
        for (Node node : vboxVenta.getChildren()) {
            if (node instanceof HBox hbox && producto.getNombre().equals(hbox.getUserData())) {
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

    private void cargarProductosDisponibles() {
        BaseDatos bd = new BaseDatos();
        Compras_DAO comprasDAO = new Compras_DAO();
        comprasDAO.conexion();

        List<Producto> productos = bd.obtenerProductos();
        todosLosProductos = productos.stream()
                .filter(producto -> comprasDAO.obtenerDisponibilidad(producto.getId()))
                .collect(Collectors.toList());

        mostrarProductos(todosLosProductos);
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
                    if (event.getClickCount() == 1) {
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
        if (vboxVenta.getChildren().isEmpty()) {
            AlertaUtil.mostrarError("Error", "No hay productos en el carrito.");
            return;
        }

        if (AlertaUtil.confirmar("Confirmar Compra", "¿Estás seguro de finalizar la compra?")) {
            Compras_DAO comprasDAO = new Compras_DAO();
            RealizarPedido_DAO pedidoDAO = new RealizarPedido_DAO();
            comprasDAO.conexion();
            pedidoDAO.conexion();

            String username = Sesion.getInstancia().getNombreUsuario();
            int idUsuario = bd.obtenerIdUsuario(username);
            if (idUsuario == -1) {
                AlertaUtil.mostrarError("Error", "No se pudo obtener el id del usuario.");
                return;
            }

            // Tomar proveedor del primer producto
            HBox primerProductoHBox = (HBox) vboxVenta.getChildren().get(0);
            String nombreProducto = (String) primerProductoHBox.getUserData();
            Producto productoCarrito = todosLosProductos.stream()
                    .filter(p -> p.getNombre().equals(nombreProducto))
                    .findFirst().orElse(null);

            if (productoCarrito == null) {
                AlertaUtil.mostrarError("Error", "No se encontró el producto en la lista.");
                return;
            }

            List<Proveedor> proveedores = pedidoDAO.obtenerProveedoresPorProducto(productoCarrito.getId());
            if (proveedores.isEmpty()) {
                AlertaUtil.mostrarError("Error", "No hay proveedor para el producto.");
                return;
            }

            int idProveedor = proveedores.get(0).getId();
            String fechaActual = LocalDate.now().toString();

            if (!comprasDAO.agregarCompra(fechaActual, total, idProveedor, idUsuario)) {
                AlertaUtil.mostrarError("Error", "No se pudo registrar la compra.");
                return;
            }

            int idCompra = comprasDAO.obtenerUltimoIdCompra();

            for (Node node : vboxVenta.getChildren()) {
                if (node instanceof HBox hbox) {
                    String nombreProd = (String) hbox.getUserData();
                    Label cantidadLabel = (Label) hbox.lookup(".cart-qty");
                    int cantidadVendida = Integer.parseInt(cantidadLabel.getText());

                    Producto prod = todosLosProductos.stream()
                            .filter(p -> p.getNombre().equals(nombreProd))
                            .findFirst().orElse(null);

                    if (prod != null) {
                        // Validar si se intenta vender más que el stock
                        if (!comprasDAO.validarCantidadDisponible(prod.getId(), cantidadVendida)) {
                            AlertaUtil.mostrarError("Stock insuficiente", "Intentas comprar más de lo disponible en stock para el producto: " + prod.getNombre());
                            return; // Cancela la compra completa
                        }

                        // Actualizar stock solo si hay suficiente cantidad
                        boolean actualizado = comprasDAO.actualizarStockProducto(prod.getId(), cantidadVendida);
                        if (!actualizado) {
                            AlertaUtil.mostrarError("Error", "No se pudo actualizar el stock para el producto: " + prod.getNombre());
                            return;
                        }

                        int nuevoStock = prod.getCantidad() - cantidadVendida;
                        prod.setCantidad(nuevoStock);

                        if (nuevoStock <= 0) {
                            bd.darDeBajaProducto(prod.getId());
                            comprasDAO.actualizarDisponibilidad(prod.getId(), false);
                        } else {
                            bd.actualizarProductoEnBaseDeDatos(prod);
                        }

                        double montoFinal = cantidadVendida * prod.getPrecioVenta();
                        if (!comprasDAO.agregarDetalleCompra(prod.getId(), idCompra, cantidadVendida, montoFinal)) {
                            AlertaUtil.mostrarError("Error", "No se pudo registrar el detalle.");
                            return;
                        }
                    }
                }
            }

            vboxVenta.getChildren().clear();
            total = 0.0;
            totalLabel.setText("$0.00");
            todosLosProductos = bd.obtenerProductos();
            mostrarProductos(todosLosProductos);

            AlertaUtil.mostrarInfo("Compra Finalizada", "La compra fue realizada exitosamente.");
        }
    }
}
