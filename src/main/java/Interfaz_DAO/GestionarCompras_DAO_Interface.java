package Interfaz_DAO;

public interface GestionarCompras_DAO_Interface {
    boolean conexion();
    boolean agregarCompra(String fecha, double total, int idProveedor, int idUsuario);
    boolean agregarDetalleCompra(int idProducto, int idCompra, int cantidad, double montoFinal);
    int obtenerUltimoIdCompra();
}
