package BaseDatos;

import Interfaz_DAO.Login_DAO_Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login_DAO implements Login_DAO_Interface {
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
    public String saberRol(String username, String password) {
        try {
            if (con == null || con.isClosed()) {
                conexion(); // Asegura que haya conexión activa
            }
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL obtener_rol('" + username + "', '" + password + "')");
            if (resultado.next()) {
                return resultado.getString("Rol");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener rol: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean validarUsuario(String username, String password) {
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL validacion_usuario('" + username + "', '" + password + "')");
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public int obtenerIdUsuario(String username) {
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT id_Usuario FROM Usuario WHERE Nombre_usuario = '" + username + "'");
            if (resultado.next()) {
                return resultado.getInt("id_Usuario");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el id del usuario: " + e.getMessage());
        }
        return -1;
    }
}
