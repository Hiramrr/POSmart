package Interfaz_DAO;

public interface AgregarEmpleado_DAO_Interface {
    public boolean conexion();
    public boolean agregarEmpleado(int id, String nombre, String contraseña, String nombreCompleto,
                                   String rol, String telefono, String ciudad, String direccion);
}
