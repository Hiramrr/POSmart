package Interfaz_DAO;

public interface Recuperar_DAO_Interface {
    public boolean conexion();

    public boolean validarNombreCompleto(String nombre_completo);
    public boolean cambiarContraseña(String nombre_completo, String contraseña_nueva);
}
