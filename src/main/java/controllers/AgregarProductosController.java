package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import BaseDatos.BaseDatos;
import javafx.stage.Stage;

import java.util.List;

public class AgregarProductosController {

    // Definición de componentes de la UI
    @FXML
    private TextField IDP;
    @FXML
    private TextField NomP;
    @FXML
    private TextArea DescP;
    @FXML
    private TextField CantP;
    @FXML
    private TextField PreP;
    @FXML
    private TextField PreCompraP;
    @FXML
    private ComboBox<String> catPCb;
    @FXML
    private ComboBox<String> ubiPCb;
    @FXML
    private Button GuardarP;
    @FXML
    private Button CancelarP;

    private BaseDatos baseDatos;

    AlertPOSmart alerta;

    private ProductosController productosController;

    // Constructor de la clase
    public AgregarProductosController() {
        baseDatos = new BaseDatos(); // Asegúrate de que tu clase BaseDatos tenga métodos para interactuar con la DB.
    }

    // Método de inicialización llamado automáticamente al cargar el FXML
    @FXML
    private void initialize() {
        // Verificar que los componentes estén correctamente inicializados
        if (GuardarP != null) {
            System.out.println("Botón GuardarP inicializado correctamente");
            GuardarP.setOnAction(e -> handleGuardarProducto()); // Vincular el botón a su acción
        } else {
            System.out.println("El botón GuardarP es null");
        }
        if (CancelarP != null) {
            System.out.println("Botón CancelarP inicializado correctamente");
            CancelarP.setOnAction(e -> handleCancelarProducto()); // Vincular el botón cancelar a su acción
        } else {
            System.out.println("El botón CancelarP es null");
        }

        if (IDP != null) {
            System.out.println("IDP está correctamente inicializado");
        } else {
            System.out.println("IDP es null");
        }

        cargarCategorias();
        cargarUbicaciones();


    }

    private void cargarCategorias() {
        List<String> categorias = baseDatos.obtenerNombresCategorias();
        catPCb.getItems().clear();
        catPCb.getItems().addAll(categorias);
    }

    private void cargarUbicaciones() {
        List<String> ubicaciones = baseDatos.obtenerNombresUbicaciones();
        ubiPCb.getItems().clear();
        ubiPCb.getItems().addAll(ubicaciones);
    }

    @FXML
    private void handleGuardarProducto() {
        System.out.println("Validando datos");

        // Verificar si los campos están vacíos
        if (IDP.getText().isEmpty() || NomP.getText().isEmpty() || DescP.getText().isEmpty() ||
                CantP.getText().isEmpty() || PreP.getText().isEmpty() ||
                catPCb.getValue() == null || ubiPCb.getValue() == null) {
            alerta = new AlertPOSmart(AlertType.WARNING, "Campos Vacíos","Por favor, complete todos los campos antes de guardar el producto." );
            return;
        }

        try {
            System.out.println("Recogiendo datos del producto");

            // Recoger los datos del formulario
            int productoId = Integer.parseInt(IDP.getText());
            String productoNombre = NomP.getText();
            String productoDescripcion = DescP.getText();
            int productoCantidad = Integer.parseInt(CantP.getText());
            Double productoPrecioCompra = Double.parseDouble(PreCompraP.getText());
            Double productoPrecioVenta = Double.parseDouble(PreP.getText());
            String productoCategoria = catPCb.getValue(); // Obtener la categoría seleccionada
            String productoUbicacion = ubiPCb.getValue(); // Obtener la ubicación seleccionada

            // Intentar agregar el producto a la base de datos
            boolean success = baseDatos.agregarProducto(productoId, productoNombre, productoDescripcion,
                    productoCantidad, productoPrecioCompra,
                    productoPrecioVenta, productoCategoria, productoUbicacion);

            // Mostrar una alerta con el resultado
            if(success){
                alerta = new AlertPOSmart(AlertType.INFORMATION, "Agregar Producto", "Producto agregado exitosamente.");
            } else {
                alerta = new AlertPOSmart(AlertType.ERROR, "Agregar Producto", "Error al agregar el producto.");
            }

            // Si la operación fue exitosa, limpiar los campos
            if (success) {
                System.out.println("si limpia los campos");
                limpiarCampos();
                if (productosController != null) {
                    System.out.println("si actualiza la tabla");
                    productosController.actualizarTabla(); // <<--- actualizamos la tabla de la otra pantalla
                    // Código para cerrar la ventana actual
                    Stage stage = (Stage) GuardarP.getScene().getWindow();
                    stage.close();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de los datos");
            // Si hay un error de formato en los números, mostrar una alerta
            alerta = new AlertPOSmart(AlertType.ERROR,"Error de Formato", "El ID, cantidad y precios deben ser números válidos." );
        }
    }


    @FXML
    private void handleCancelarProducto() {
        System.out.println("Cancelando...");
        limpiarCampos(); // Limpiar los campos
        Stage stage = (Stage) CancelarP.getScene().getWindow();
        stage.close();
    }


    private void limpiarCampos() {
        IDP.clear();
        NomP.clear();
        DescP.clear();
        CantP.clear();
        PreP.clear();
        PreCompraP.clear();
        catPCb.getSelectionModel().clearSelection();
        ubiPCb.getSelectionModel().clearSelection();
    }

    //----------------------------------------------------------
    public void setProductosController(ProductosController productosController) {
        this.productosController = productosController;
    }




}

