package BaseDatos;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;

public class BaseDatos {
    private static Connection con;

    private static Statement consulta;
    private static ResultSet resultado;

    public BaseDatos() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/POSMart", "Hiram", "coco123");
            System.out.println("Si ves esto es que se conecto la base");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean validarUsuario(String username, String password){
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

    public boolean validarNombreCompleto(String nombre_completo){
        try {
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

    public boolean cambiarContraseña(String nombre_completo, String contraseña_nueva){
        try {
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

    public boolean agregarEmpleado(int id, String nombre, String contraseña, String nombre_completo, String rol, String Telefono, String ciudad, String direccion){
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL agregar_usuario('" + id + "', '" + nombre + "', '" + contraseña + "', '" + nombre_completo + "', '" + rol + "', '" + Telefono + "', '" + ciudad + "', '" + direccion + "')");
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


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




}