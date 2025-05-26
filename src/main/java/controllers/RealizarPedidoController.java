package controllers;

import BaseDatos.RealizarPedido_DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RealizarPedidoController implements Initializable {

    @FXML
    private Button buttonRealizarPedido;

    @FXML
    private TextField cantidad;

    @FXML
    private TableColumn<PedidoItem, Integer> cantidadProducto;

    @FXML
    private TableColumn<PedidoItem, Integer> idProducto;

    @FXML
    private TableColumn<PedidoItem, String> nombreProducto;

    @FXML
    private TableColumn<PedidoItem, String> nombreProveedor;

    @FXML
    private ChoiceBox<Producto> productosBox;

    @FXML
    private ChoiceBox<Proveedor> proveedorBox;

    @FXML
    private TableView<PedidoItem> tableProvee;

    @FXML
    private TableColumn<PedidoItem, Double> total;

    private RealizarPedido_DAO realizarPedidoDAO;
    private ObservableList<PedidoItem> pedidoItems;

    @FXML
    private Text totalPedido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        realizarPedidoDAO = new RealizarPedido_DAO();
        realizarPedidoDAO.conexion();
        
        configurarTabla();
        cargarPedidos();
        cargarProductos();
        
        productosBox.setOnAction(event -> {
            Producto productoSeleccionado = productosBox.getValue();
            if (productoSeleccionado != null) {
                cargarProveedoresProducto(productoSeleccionado.getId());
            }
            actualizarTotal();
        });

        proveedorBox.setConverter(new StringConverter<Proveedor>() {
            @Override
            public String toString(Proveedor proveedor) {
                return proveedor != null ? proveedor.getNombre() : "";
            }

            @Override
            public Proveedor fromString(String string) {
                return null;
            }
        });
        cantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            actualizarTotal();
        });
        totalPedido.setText("$0.00");
    }

    private void configurarTabla() {
        idProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        nombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        cantidadProducto.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        nombreProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        pedidoItems = FXCollections.observableArrayList();
        tableProvee.setItems(pedidoItems);
    }

    private void cargarPedidos() {
        pedidoItems.clear();
        pedidoItems.addAll(realizarPedidoDAO.obtenerPedidos());
    }

    private void cargarProductos() {
        ObservableList<Producto> productos = realizarPedidoDAO.obtenerProductos();
        productosBox.setItems(productos);
        
        productosBox.setConverter(new StringConverter<Producto>() {
            @Override
            public String toString(Producto producto) {
                return producto != null ? producto.getNombre() : "";
            }

            @Override
            public Producto fromString(String string) {
                return null;
            }
        });
    }

    private void cargarProveedoresProducto(int idProducto) {
        List<Proveedor> proveedores = realizarPedidoDAO.obtenerProveedoresPorProducto(idProducto);
        if (!proveedores.isEmpty()) {
            proveedorBox.setItems(FXCollections.observableArrayList(proveedores));
            proveedorBox.setValue(proveedores.get(0));
        } else {
            proveedorBox.setItems(FXCollections.observableArrayList());
            proveedorBox.setValue(null);
            mostrarAlerta("Error", "Este producto no tiene proveedores asignados");
        }
    }

    @FXML
    void handleRealizarPedido(ActionEvent event) {
        Producto productoSeleccionado = productosBox.getValue();
        if (productoSeleccionado == null) {
            mostrarAlerta("Error", "Por favor seleccione un producto");
            return;
        }

        Proveedor proveedorSeleccionado = proveedorBox.getValue();
        if (proveedorSeleccionado == null) {
            mostrarAlerta("Error", "Por favor seleccione un proveedor");
            return;
        }

        String cantidadStr = cantidad.getText();
        if (cantidadStr.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese una cantidad");
            return;
        }

        try {
            int cantidadPedido = Integer.parseInt(cantidadStr);
            if (cantidadPedido <= 0) {
                mostrarAlerta("Error", "La cantidad debe ser mayor a 0");
                return;
            }
            
            double total = cantidadPedido * productoSeleccionado.getPrecioCompra();

            boolean exitoso = realizarPedidoDAO.realizarPedido(
                productoSeleccionado.getId(),
                proveedorSeleccionado.getId(),
                cantidadPedido,
                total,
                productoSeleccionado.getNombre(),
                proveedorSeleccionado.getNombre()
            );

            if (exitoso) {
                PedidoItem pedidoItem = new PedidoItem(
                    productoSeleccionado.getId(),
                    productoSeleccionado.getNombre(),
                    cantidadPedido,
                    proveedorSeleccionado.getNombre(),
                    total
                );
                pedidoItems.add(pedidoItem);
                
                productosBox.setValue(null);
                proveedorBox.setValue(null);
                cantidad.clear();
                
                mostrarAlerta("Éxito", "Pedido realizado correctamente");
            } else {
                mostrarAlerta("Error", "No se pudo realizar el pedido");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La cantidad debe ser un número válido");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        AlertPOSmart alerta = new AlertPOSmart(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    private void actualizarTotal() {
        Producto productoSeleccionado = productosBox.getValue();
        if (productoSeleccionado == null) {
            totalPedido.setText("$0.00");
            return;
        }

        try {
            String cantidadStr = cantidad.getText();
            if (cantidadStr.isEmpty()) {
                totalPedido.setText("$0.00");
                return;
            }

            int cantidadPedido = Integer.parseInt(cantidadStr);
            if (cantidadPedido <= 0) {
                totalPedido.setText("$0.00");
                return;
            }

            double total = cantidadPedido * productoSeleccionado.getPrecioCompra();
            totalPedido.setText(String.format("$%.2f", total));

        } catch (NumberFormatException e) {
            totalPedido.setText("????");
        }
    }
}
