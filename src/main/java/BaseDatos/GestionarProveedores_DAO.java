package BaseDatos;

import Interfaz_DAO.GestionarProveedores_DAO_Interface;
import controllers.Proveedor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestionarProveedores_DAO implements GestionarProveedores_DAO_Interface {
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
    public List<Proveedor> obtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT * FROM Proveedor");
            while (resultado.next()) {
                Proveedor proveedor = new Proveedor(
                        resultado.getInt("id_Proveedor"),
                        resultado.getString("Nombre"),
                        resultado.getString("Telefono"),
                        resultado.getString("Correo"),
                        resultado.getString("Direccion")
                );
                proveedores.add(proveedor);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return proveedores;
    }

    @Override
    public boolean agregarProveedor(int id, String nombre, String telefono, String correo, String direccion) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_proveedor('" + id + "', '" + nombre + "', '" + telefono + "', '" + correo + "', '" + direccion + "')";
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
    public boolean modificarProveedor(int id, String nombre, String telefono, String correo, String direccion) {
        try {
            consulta = con.createStatement();
            String query = "CALL modificar_proveedor('" + id + "', '" + nombre + "', '" + telefono + "', '" + correo + "', '" + direccion + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean eliminarProveedor(int id) {
        try {
            consulta = con.createStatement();
            String query = "CALL eliminar_proveedor('" + id + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
