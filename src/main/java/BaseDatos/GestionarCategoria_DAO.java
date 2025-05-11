package BaseDatos;

import Interfaz_DAO.GestionarCategoria_DAO_Interface;
import controllers.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestionarCategoria_DAO implements GestionarCategoria_DAO_Interface {
    private static Connection con;

    private static Statement consulta;
    private static ResultSet resultado;

    @Override
    public boolean conexion() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/POSMart", "Hiram", "coco123");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT * FROM Categoria");
            while (resultado.next()) {
                Categoria categoria = new Categoria(
                        resultado.getInt("id_Categoria"),
                        resultado.getString("Nombre"),
                        resultado.getString("Descripcion")
                );
                categorias.add(categoria);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return categorias;
    }

    @Override
    public boolean agregarCategoria(int id, String nombre, String descripcion) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_Categoria('" + id + "', '" + nombre + "', '" + descripcion + "')";
            resultado = consulta.executeQuery(query);
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean eliminarCategoria(int id) {
        try {
            consulta = con.createStatement();
            String query = "CALL eliminar_Categoria('" + id + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean modificarCategoria(int id, String nombre, String descripcion) {
        try {
            consulta = con.createStatement();
            String query = "CALL modificar_Categoria('" + id + "', '" + nombre + "', '" + descripcion + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
