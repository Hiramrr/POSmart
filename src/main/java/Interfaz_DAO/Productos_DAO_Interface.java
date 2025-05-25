package Interfaz_DAO;

import controllers.Producto;
import javafx.collections.ObservableList;

import java.util.List;

public interface Productos_DAO_Interface {
    public boolean conexion();
    public ObservableList<Producto> obtenerProductos();
    public List<String> obtenerNombresCategorias();
    public List<String> obtenerNombresUbicaciones();
    public boolean eliminarProductoDeBaseDeDatos(int idProducto);
    public ObservableList<Producto> obtenerProductosActivos();
    public boolean agregarStock(int idProducto, int cantidad);
    }
