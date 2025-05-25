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
            buscarProducto(newValue);
        });
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
            AlertaUtil.mostrarInfo("Sin resultados", "No se encontraron productos que coincidan con la búsqueda.");
        }

        mostrarProductos(filtrados);
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
        if (AlertaUtil.confirmar("Confirmar Compra", "¿Estás seguro de finalizar la compra?")) {
            Compras_DAO comprasDAO = new Compras_DAO();
            comprasDAO.conexion();

            // Obtener el username desde la sesión
            String username = Sesion.getInstancia().getNombreUsuario();
            int idUsuario = bd.obtenerIdUsuario(username);

            if (idUsuario == -1) {
                AlertaUtil.mostrarError("Error", "No se pudo obtener el id del usuario.");
                return;
            }

            //Temporalmente por pruebas usar el mismo provedor para todos
            String nombreProveedor = "Proveedor A";
            int idProveedor = bd.obtenerIdProveedor(nombreProveedor);

            if (idProveedor == -1) {
                AlertaUtil.mostrarError("Error", "No se pudo obtener el id del proveedor.");
                return;
            }

            String fechaActual = LocalDate.now().toString();
            double totalCompra = total;

            boolean compraExitosa = comprasDAO.agregarCompra(fechaActual, totalCompra, idProveedor, idUsuario);
            if (!compraExitosa) {
                AlertaUtil.mostrarError("Error", "No se pudo registrar la compra.");
                return;
            }

            int idCompra = comprasDAO.obtenerUltimoIdCompra();

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

                        if (nuevoStock <= 0) {
                            bd.eliminarProductoDeBaseDeDatos(productoOriginal.getId());
                        } else {
                            bd.actualizarProductoEnBaseDeDatos(productoOriginal);
                        }

                        double montoFinal = cantidadVendida * productoOriginal.getPrecioVenta();
                        boolean detalleExitoso = comprasDAO.agregarDetalleCompra(productoOriginal.getId(), idCompra, cantidadVendida, montoFinal);
                        if (!detalleExitoso) {
                            AlertaUtil.mostrarError("Error", "No se pudo registrar el detalle de la compra.");
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

            AlertaUtil.mostrarInfo("Compra Finalizada", "La compra se realizó con éxito.");
        }
    }



}
