package Interfaz_DAO;

import controllers.Categoria;
import controllers.Ubicacion;

import java.util.List;

public interface GestionarUbicacion_DAO_Interface {
    public boolean conexion();
    public List<Ubicacion> obtenerUbicacion();
    public boolean agregarUbicacion(int id, String nombre, String descripcion);
    public boolean eliminarUbicacion(int id);
    public boolean modificarUbicacion(int id, String nombre, String descripcion);
}
