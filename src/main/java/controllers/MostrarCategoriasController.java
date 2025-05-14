package controllers;

import BaseDatos.GestionarCategoria_DAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class MostrarCategoriasController {

    @FXML
    private TableView<Categoria> tablaCategoria;

    @FXML
    private TableColumn<Categoria, Integer> idCategoria;

    @FXML
    private TableColumn<Categoria, String> nombreCategoria;

    @FXML
    private TableColumn<Categoria, String> descripcionCategoria;

    private final GestionarCategoria_DAO dao = new GestionarCategoria_DAO();
    AlertPOSmart alerta;

    @FXML
    private void initialize() {
        if (!dao.conexion()) {
            alerta = new AlertPOSmart(Alert.AlertType.ERROR, "No hay conexion", "No hay conexion con la base de datos por favor contacta a un administrador");
            return;
        }

        idCategoria.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        cargarCategorias();
    }

    private void cargarCategorias() {
        List<Categoria> categorias = dao.obtenerCategorias();
        tablaCategoria.getItems().setAll(categorias);
    }
}
