package BaseDatos;

import Interfaz_DAO.GestionarCompras_DAO_Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Compras_DAO implements GestionarCompras_DAO_Interface {
    private static Connection con;
    private static Statement consulta;
    private static ResultSet resultado;

    @Override
    public boolean conexion() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/POSMart", "Hiram", "coco123");
            return true;
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean agregarCompra(String fecha, double total, int idProveedor, int idUsuario) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_Compra(NULL, '" + fecha + "', '" + total + "', '" + idProveedor + "', '" + idUsuario + "')";
            resultado = consulta.executeQuery(query);
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al insertar compra: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean agregarDetalleCompra(int idProducto, int idCompra, int cantidad, double montoFinal) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_DetalleCompra(NULL, '" + idProducto + "', '" + idCompra + "', '" + cantidad + "', '" + montoFinal + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error al insertar detalle de compra: " + e.getMessage());
        }
        return false;
    }

    public int obtenerUltimoIdCompra() {
        int idCompra = -1;
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultado.next()) {
                idCompra = resultado.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el último id de compra: " + e.getMessage());
        }
        return idCompra;
    }
}
