package controllers;

import BaseDatos.BaseDatos;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class ProductosController {



    @FXML
    private TableView<Producto> TProductos;

    @FXML
    private TableColumn<Producto, Integer> IDCol;

    @FXML
    private TableColumn<Producto, String> NomCol;

    @FXML
    private TableColumn<Producto, String> DesCol;

    @FXML
    private TableColumn<Producto, Integer> CantCol;

    @FXML
    private TableColumn<Producto, Double> PrecioCol;

    @FXML
    private Button OpAgregar;

    @FXML
    private Button OpEliminar;

    @FXML
    private Button OpEditar;

    @FXML
    private Button OpListar;

    @FXML
    private Button OpConsultar;

    @FXML
    private ImageView ImgProducto;

    @FXML
    private Text textDia;

    @FXML
    private Text textHora;

    private BaseDatos baseDatos;


    @FXML
    private void initialize() {
        System.out.println("inicializar");
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomCol.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        CantCol.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
        PrecioCol.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        System.out.println("si paso");

        ///////////////////////////////////////
        this.baseDatos = new BaseDatos();
        ObservableList<Producto> productos = baseDatos.obtenerProductos();
        TProductos.setItems(productos);
        System.out.println("si obtuvo los productos");

    }

    /**@FXML
    private void agregarProducto(ActionEvent event) {
        System.out.println("Clic en 'Agregar Producto'");
        try {
            System.out.println("carga el archivo");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AgregarProductos.fxml"));
            AnchorPane root = loader.load();
            System.out.println("carga el contenido del archivo");

            System.out.println("Error en el archivo");
            AgregarProductosController controller = loader.getController();
            //controller.setProductosController(this);
            System.out.println("crea una nueva ventana");
            System.out.println("stage para la ventana");
            Stage stage = new Stage();
            stage.setTitle("Agregar Producto");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println("Error en el archivo");
            e.printStackTrace();
        }
    }*/
    @FXML
    private void agregarProducto(ActionEvent event) {
        System.out.println("Clic en 'Agregar Producto'");
        try {
            System.out.println("carga el archivo");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AgregarProductos.fxml"));
            AnchorPane root = loader.load();
            System.out.println("carga el contenido del archivo");

            AgregarProductosController controller = loader.getController();
            controller.setProductosController(this); // 💬 Aquí le pasas la referencia

            System.out.println("crea una nueva ventana");
            Stage stage = new Stage();
            stage.setTitle("Agregar Producto");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println("Error en el archivo");
            e.printStackTrace();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void actualizarTabla() {
        System.out.println("entra a actualizar tabla");
        if (baseDatos == null) {
            baseDatos = new BaseDatos(); // Si todavía no tienes una instancia
        }
        System.out.println("si se actualiza");
        ObservableList<Producto> listaProductos = baseDatos.obtenerProductos(); // Este método deberías tenerlo en BaseDatos
        TProductos.setItems(listaProductos);
    }




    @FXML
    private void eliminarProducto(ActionEvent event) {
        System.out.println("Eliminar Producto");
    }

    @FXML
    private void editarProducto(ActionEvent event) {
        System.out.println("Editar Producto");
    }

    @FXML
    private void consultarProducto(ActionEvent event) {
        System.out.println("Consultar Producto");
    }

    private void llenarCampos(Producto producto) {
        // Asumiendo que tienes campos de texto en tu interfaz para cada atributo del producto
        IDCol.setText(String.valueOf(producto.getId()));
        NomCol.setText(producto.getNombre());
        DesCol.setText(producto.getDescripcion());
        CantCol.setText(String.valueOf(producto.getCantidad()));
        PrecioCol.setText(String.valueOf(producto.getPrecio()));

    }


}

