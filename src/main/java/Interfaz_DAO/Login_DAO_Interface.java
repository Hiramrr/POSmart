package Interfaz_DAO;

public interface Login_DAO_Interface {
    public boolean conexion();
    public String saberRol(String username, String password);
    public boolean validarUsuario(String username, String password);
    public int obtenerIdUsuario(String username);
}
