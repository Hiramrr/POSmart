package BaseDatos;

import Interfaz_DAO.GestionarUbicacion_DAO_Interface;
import controllers.Categoria;
import controllers.Ubicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestionarUbicacion_DAO implements GestionarUbicacion_DAO_Interface {
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
    public List<Ubicacion> obtenerUbicacion() {
        List<Ubicacion> ubicaciones = new ArrayList<>();
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT * FROM Ubicacion");
            while (resultado.next()) {
                Ubicacion ubicacion = new Ubicacion(
                        resultado.getInt("id_Ubicacion"),
                        resultado.getString("Nombre"),
                        resultado.getString("Descripcion")
                );
                ubicaciones.add(ubicacion);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ubicaciones;
    }

    @Override
    public boolean agregarUbicacion(int id, String nombre, String descripcion) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_Ubicacion('" + id + "', '" + nombre + "', '" + descripcion +  "')";
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
    public boolean eliminarUbicacion(int id) {
        try {
            consulta = con.createStatement();
            String query = "CALL eliminar_ubicacion('" + id + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean modificarUbicacion(int id, String nombre, String descripcion) {
        try {
            consulta = con.createStatement();
            String query = "CALL modificar_Ubicacion('" + id + "', '" + nombre + "', '" + descripcion + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
