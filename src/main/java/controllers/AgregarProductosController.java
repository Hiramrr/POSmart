package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import BaseDatos.BaseDatos;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

public class AgregarProductosController {

    @FXML private TextField IDP, NomP, CantP, PreP, PreCompraP;
    @FXML private TextArea DescP;
    @FXML private ComboBox<String> catPCb, ubiPCb, estadoCb;
    @FXML private ComboBox<Proveedor> proveedorCb;
    @FXML private Button GuardarP, CancelarP;
    @FXML private ImageView imagen;

    private BaseDatos baseDatos = new BaseDatos();
    private File imagenSeleccionada;
    private AlertPOSmart alerta;
    private ProductosController productosController;

    @FXML
    public void initialize() {
        cargarProveedores();
        cargarCategorias();
        cargarUbicaciones();
        cargarEstados();

        if (GuardarP != null) GuardarP.setOnAction(e -> handleGuardarProducto());
        if (CancelarP != null) CancelarP.setOnAction(e -> handleCancelarProducto());
    }

    private void cargarProveedores() {
        List<Proveedor> proveedores = baseDatos.obtenerProveedores();
        proveedorCb.setItems(FXCollections.observableArrayList(proveedores));
        proveedorCb.setConverter(new StringConverter<Proveedor>() {
            @Override public String toString(Proveedor proveedor) { return proveedor != null ? proveedor.getNombre() : ""; }
            @Override public Proveedor fromString(String string) { return null; }
        });
    }

    private void cargarCategorias() {
        catPCb.getItems().setAll(baseDatos.obtenerNombresCategorias());
    }

    private void cargarUbicaciones() {
        ubiPCb.getItems().setAll(baseDatos.obtenerNombresUbicaciones());
    }

    private void cargarEstados() {
        estadoCb.getItems().setAll("Activo", "Inactivo");
        estadoCb.setValue("Activo");
    }

    @FXML
    private void handleGuardarProducto() {
        if (IDP.getText().isEmpty() || NomP.getText().isEmpty() || DescP.getText().isEmpty() ||
                CantP.getText().isEmpty() || PreP.getText().isEmpty() || PreCompraP.getText().isEmpty() ||
                catPCb.getValue() == null || ubiPCb.getValue() == null || proveedorCb.getValue() == null) {
            alerta = new AlertPOSmart(AlertType.WARNING, "Campos Vacíos", "Por favor, complete todos los campos.");
            return;
        }

        try {
            int productoId = Integer.parseInt(IDP.getText());
            String productoNombre = NomP.getText();
            String productoDescripcion = DescP.getText();
            int productoCantidad = Integer.parseInt(CantP.getText());
            double productoPrecioCompra = Double.parseDouble(PreCompraP.getText());
            double productoPrecioVenta = Double.parseDouble(PreP.getText());
            String productoCategoria = catPCb.getValue();
            String productoUbicacion = ubiPCb.getValue();
            Proveedor proveedorSeleccionado = proveedorCb.getValue();

            byte[] imagenBytes = null;
            if (imagen.getImage() != null) {
                BufferedImage bImage = SwingFXUtils.fromFXImage(imagen.getImage(), null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", baos);
                imagenBytes = baos.toByteArray();
            }

            boolean success = baseDatos.agregarProducto(productoId, productoNombre, productoDescripcion,
                    productoCantidad, productoPrecioCompra, productoPrecioVenta,
                    productoCategoria, productoUbicacion, imagenBytes);

            if (success) {
                boolean relacionExitosa = baseDatos.agregarProductoProveedor(productoId, proveedorSeleccionado.getId());
                if (relacionExitosa) {
                    alerta = new AlertPOSmart(AlertType.INFORMATION, "Producto Agregado",
                            "Producto y relación con proveedor guardados exitosamente.");
                } else {
                    alerta = new AlertPOSmart(AlertType.WARNING, "Relación con proveedor",
                            "Producto guardado, pero falló la relación con proveedor.");
                }
                limpiarCampos();
                if (productosController != null) {
                    productosController.actualizarTabla();
                    ((Stage) GuardarP.getScene().getWindow()).close();
                }
            } else {
                alerta = new AlertPOSmart(AlertType.ERROR, "Error", "No se pudo agregar el producto.");
            }

        } catch (NumberFormatException e) {
            alerta = new AlertPOSmart(AlertType.ERROR, "Error de Formato", "ID, cantidad y precios deben ser números.");
        } catch (IOException e) {
            alerta = new AlertPOSmart(AlertType.ERROR, "Error con la Imagen", "No se pudo convertir la imagen.");
        }
    }

    @FXML
    private void handleCancelarProducto() {
        limpiarCampos();
        ((Stage) CancelarP.getScene().getWindow()).close();
    }

    private void limpiarCampos() {
        IDP.clear(); NomP.clear(); DescP.clear(); CantP.clear();
        PreP.clear(); PreCompraP.clear();
        catPCb.setValue(null); ubiPCb.setValue(null); proveedorCb.setValue(null);
        imagen.setImage(null);
    }

    public void setProductosController(ProductosController productosController) {
        this.productosController = productosController;
    }

    @FXML
    private void handlerAgregarImagens() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File archivo = fileChooser.showOpenDialog(null);
        if (archivo != null) {
            imagenSeleccionada = archivo;
            imagen.setImage(new Image(archivo.toURI().toString()));
        } else {
            alerta = new AlertPOSmart(AlertType.WARNING, "Imagen", "No se seleccionó ninguna imagen.");
        }
    }
}
