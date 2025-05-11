package Interfaz_DAO;

import controllers.Proveedor;

import java.util.List;

public interface GestionarProveedores_DAO_Interface {
    public boolean conexion();
    public List<Proveedor> obtenerProveedores();
    public boolean agregarProveedor(int id, String nombre, String telefono, String correo, String direccion);
    public boolean modificarProveedor(int id, String nombre, String telefono, String correo, String direccion);
    public boolean eliminarProveedor(int id);
}
