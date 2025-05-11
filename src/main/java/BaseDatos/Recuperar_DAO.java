package BaseDatos;

import Interfaz_DAO.Recuperar_DAO_Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Recuperar_DAO implements Recuperar_DAO_Interface {
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
    public boolean validarNombreCompleto(String nombre_completo) {
        try{
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL existe_usuario('" + nombre_completo + "')");
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean cambiarContraseña(String nombre_completo, String contraseña_nueva) {
        try{
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL cambiar_contraseña('" + nombre_completo + "', '" + contraseña_nueva + "')");
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
