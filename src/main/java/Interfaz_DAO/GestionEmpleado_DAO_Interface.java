package Interfaz_DAO;

import controllers.Empleado;

import java.util.List;

public interface GestionEmpleado_DAO_Interface {
    public boolean conexion();
    public boolean editarEmpleado(int id, String nombre, String contraseña, String nombreCompleto,
                                  String rol, String telefono, String ciudad, String direccion);
    public boolean eliminarEmpleado(int id);
    public List<Empleado> obtenerEmpleados();
}
