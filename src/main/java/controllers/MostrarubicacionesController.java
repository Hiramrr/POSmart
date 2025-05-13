package controllers;

import BaseDatos.GestionarUbicacion_DAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class MostrarubicacionesController {

    @FXML
    private TableView<Ubicacion> tableUbicacion;

    @FXML
    private TableColumn<Ubicacion, Integer> idUbicacion;

    @FXML
    private TableColumn<Ubicacion, String> nombreUbicacion;

    @FXML
    private TableColumn<Ubicacion, String> descripcionUbicacion;

    private final GestionarUbicacion_DAO mBD = new GestionarUbicacion_DAO();

    @FXML
    private void initialize() {
        // Verifica conexión con la BD
        if (!mBD.conexion()) {
            System.err.println("No se pudo conectar a la base de datos.");
            return;
        }

        // Configura columnas
        idUbicacion.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreUbicacion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionUbicacion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Carga los datos
        cargarUbicaciones();
    }

    private void cargarUbicaciones() {
        List<Ubicacion> ubicaciones = mBD.obtenerUbicacion();
        tableUbicacion.getItems().setAll(ubicaciones);
    }
}
