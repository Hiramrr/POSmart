package Interfaz_DAO;

import controllers.Categoria;

import java.util.List;

public interface GestionarCategoria_DAO_Interface {
    public boolean conexion();
    public List<Categoria> obtenerCategorias();
    public boolean agregarCategoria(int id, String nombre, String descripcion);
    public boolean eliminarCategoria(int id);
    public boolean modificarCategoria(int id, String nombre, String descripcion);
}
