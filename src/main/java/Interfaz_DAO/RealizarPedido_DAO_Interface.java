package Interfaz_DAO;

import controllers.Producto;
import controllers.Proveedor;
import javafx.collections.ObservableList;

import java.util.List;

public interface RealizarPedido_DAO_Interface {
    public boolean conexion();
    public ObservableList<Producto> obtenerProductos();
    public List<Proveedor> obtenerProveedoresPorProducto(int idProducto);
    public boolean realizarPedido(int idProducto, int idProveedor, int cantidad, double total, String nombreProducto, String nombreProveedor);
}
