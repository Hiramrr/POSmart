package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import BaseDatos.BaseDatos;
import javafx.stage.Stage;

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
    private TextField CatP;
    @FXML
    private TextField UbiP;
    @FXML
    private Button GuardarP;
    @FXML
    private Button CancelarP;

    private BaseDatos baseDatos;

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


    }


    @FXML
    private void handleGuardarProducto() {
        System.out.println("Validando datos");

        // Verificar si los campos están vacíos
        if (IDP.getText().isEmpty() || NomP.getText().isEmpty() || DescP.getText().isEmpty() ||
                CantP.getText().isEmpty() || PreP.getText().isEmpty() ||
                CatP.getText().isEmpty() || UbiP.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campos Vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos antes de guardar el producto.");
            alert.showAndWait();
            return;
        }

        try {
            System.out.println("Recogiendo datos del producto");
            // Recoger los datos del formulario
            int productoId = Integer.parseInt(IDP.getText());
            String productoNombre = NomP.getText();
            String productoDescripcion = DescP.getText();
            int productoCantidad = Integer.parseInt(CantP.getText());
            double productoPrecio = Double.parseDouble(PreP.getText());
            String productoUbicacion = UbiP.getText();
            String productoCategoria = CatP.getText();


            // Intentar agregar el producto a la base de datos
            boolean success = baseDatos.agregarProducto(productoId, productoNombre, productoDescripcion, productoCantidad, productoPrecio, productoCategoria, productoUbicacion);

            // Mostrar una alerta con el resultado
            Alert alert = new Alert(success ? AlertType.INFORMATION : AlertType.ERROR);
            alert.setTitle("Agregar Producto");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Producto agregado exitosamente." : "Error al agregar el producto.");
            alert.showAndWait();

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
                 // Llamamos a actualizar la tabla
            }
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de los datos");
            // Si hay un error de formato en los números, mostrar una alerta
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de Formato");
            alert.setHeaderText(null);
            alert.setContentText("El ID, cantidad y precio deben ser números válidos.");
            alert.showAndWait();
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
        IDP.setText("");
        NomP.setText("");
        DescP.setText("");
        CantP.setText("");
        PreP.setText("");
        CatP.setText("");
        UbiP.setText("");
    }
//----------------------------------------------------------
    public void setProductosController(ProductosController productosController) {
        this.productosController = productosController;
    }




}

