package controllers;

import BaseDatos.BaseDatos;
import BaseDatos.Compras_DAO;
import controllers.Producto;
import controllers.Compra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import javafx.scene.control.Alert;

public class HistorialVentasController {

    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, Integer> columnaIdCompra;
    @FXML private TableColumn<Compra, String> columnaFechaCompra;
    @FXML private TableColumn<Compra, Double> columnaTotalCompra;
    @FXML private TableColumn<Compra, String> columnaProveedor;
    @FXML private TableColumn<Compra, String> columnaUsuario;
    @FXML private TableView<DetalleCompra> tablaDetallesCompra;
    @FXML private TableColumn<DetalleCompra, String> columnaProducto;
    @FXML private TableColumn<DetalleCompra, Integer> columnaCantidad;
    @FXML private TableColumn<DetalleCompra, Double> columnaPrecioUnitario;
    @FXML private TableColumn<DetalleCompra, Double> columnaMontoFinal;
    @FXML private TextField txtFechaFiltro;
    @FXML private TextField txtProveedorFiltro;
    @FXML private Button btnFiltrar;
    @FXML private Label lblIdCompra;
    @FXML private Label lblFechaCompra;
    @FXML private Label lblProveedorCompra;
    @FXML private Label lblUsuarioCompra;
    @FXML private Label lblTotalCompra;
    @FXML private Button btnGenerarFactura;

    AlertPOSmart alerta;

    private BaseDatos baseDatos;
    private ObservableList<Compra> compras;
    private ObservableList<DetalleCompra> detallesCompra;

    public HistorialVentasController() {
        baseDatos = new BaseDatos();
        compras = FXCollections.observableArrayList();
        detallesCompra = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        // Inicializar columnas de la tabla de compras
        columnaIdCompra.setCellValueFactory(cellData -> cellData.getValue().idCompraProperty().asObject());
        columnaFechaCompra.setCellValueFactory(cellData -> cellData.getValue().fechaCompraProperty());
        columnaTotalCompra.setCellValueFactory(cellData -> cellData.getValue().totalCompraProperty().asObject());
        columnaProveedor.setCellValueFactory(cellData -> cellData.getValue().proveedorProperty());
        columnaUsuario.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());

        // Inicializar columnas de la tabla de detalles de compra
        columnaProducto.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
        columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());
        columnaPrecioUnitario.setCellValueFactory(cellData -> cellData.getValue().precioUnitarioProperty().asObject());
        columnaMontoFinal.setCellValueFactory(cellData -> cellData.getValue().montoFinalProperty().asObject());

        // Cargar las compras al inicializar la pantalla
        cargarCompras();

        // Acción al seleccionar una compra
        tablaCompras.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDetallesCompra(newValue.getIdCompra());
                mostrarResumenCompra(newValue);
            }
        });

        // Acción de filtrar
//        btnFiltrar.setOnAction(event -> filtrarCompras());
    }

    // Cargar compras en la tabla
    private void cargarCompras() {
        List<Compra> listaCompras = baseDatos.obtenerCompras(); // Obtén las compras desde la base de datos
        compras.clear();
        compras.addAll(listaCompras);
        tablaCompras.setItems(compras);
    }

    // Cargar detalles de la compra seleccionada
    private void cargarDetallesCompra(int idCompra) {
        List<DetalleCompra> listaDetalles = baseDatos.obtenerDetallesCompra(idCompra); // Obtén los detalles desde la base de datos
        detallesCompra.clear();
        detallesCompra.addAll(listaDetalles);
        tablaDetallesCompra.setItems(detallesCompra);
    }

    // Mostrar resumen de la compra seleccionada
    private void mostrarResumenCompra(Compra compra) {
        lblIdCompra.setText(String.valueOf(compra.getIdCompra()));
        lblFechaCompra.setText(compra.getFechaCompra());
        lblProveedorCompra.setText(compra.getProveedor());
        lblUsuarioCompra.setText(compra.getUsuario());
        lblTotalCompra.setText(String.valueOf(compra.getTotalCompra()));
    }

//    // Filtrar compras por fecha y proveedor
//    private void filtrarCompras() {
//        String fecha = txtFechaFiltro.getText();
//        String proveedor = txtProveedorFiltro.getText();
//        List<Compra> listaFiltrada = baseDatos.obtenerComprasFiltradas(fecha, proveedor);
//        compras.clear();
//        compras.addAll(listaFiltrada);
//        tablaCompras.setItems(compras);
//    }

    @FXML
    void handleGenerarFactura(ActionEvent event) {
        Compra compraSeleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (compraSeleccionada == null) {
            mostrarAlertaWarning("Selección requerida", "Por favor, seleccione una compra para generar la factura.");
            return;
        }

        try {
            String nombreArchivo = "factura_" + compraSeleccionada.getIdCompra() + ".txt";
            FileWriter writer = new FileWriter(nombreArchivo);
            PrintWriter printWriter = new PrintWriter(writer);

            printWriter.println("======================================================================================");
            printWriter.println("                                FACTURA DE COMPRA                                     ");
            printWriter.println("======================================================================================");
            printWriter.println();

            printWriter.println("ID Compra: " + compraSeleccionada.getIdCompra());
            printWriter.println("Fecha: " + compraSeleccionada.getFechaCompra());
            printWriter.println("Proveedor: " + compraSeleccionada.getProveedor());
            printWriter.println("Usuario: " + compraSeleccionada.getUsuario());
            printWriter.println();

            printWriter.println("DETALLES DE LA COMPRA");
            printWriter.println("--------------------------------------------------------------------------------------");
            printWriter.printf("%-30s %-10s %-15s %-15s%n", "Producto", "Cantidad", "Precio Unit.", "Total");
            printWriter.println("--------------------------------------------------------------------------------------");

            for (DetalleCompra detalle : detallesCompra) {
                printWriter.printf("%-30s %-10d $%-14.2f $%-14.2f%n",
                        detalle.getProducto(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getMontoFinal());
            }

            printWriter.println("--------------------------------------------------------------------------------------");
            printWriter.printf("TOTAL: $%.2f%n", compraSeleccionada.getTotalCompra());
            printWriter.println("======================================================================================");

            printWriter.close();
            mostrarAlerta("Factura generada", "La factura se ha generado exitosamente como '" + nombreArchivo + "'");
        } catch (IOException e) {
            mostrarAlertaError("Error", "No se pudo generar la factura: " + e.getMessage());
        }
    }

    public void mostrarAlerta(String titulo, String mensaje){
        alerta = new AlertPOSmart(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    public void mostrarAlertaError(String titulo,String mensaje){
        alerta = new AlertPOSmart(Alert.AlertType.ERROR, titulo, mensaje);
    }

    public void mostrarAlertaWarning(String titulo,String mensaje){
        alerta = new AlertPOSmart(Alert.AlertType.WARNING, titulo, mensaje);
    }
}
