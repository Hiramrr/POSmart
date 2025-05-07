package BaseDatos;

import controllers.Proveedor;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import controllers.Empleado;


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

    // Método para agregar un empleado
    public boolean agregarEmpleado(int id, String nombre, String contraseña, String nombreCompleto,
                                   String rol, String telefono, String ciudad, String direccion) {
        String query = "INSERT INTO Usuario (id_Usuario, Nombre_usuario, Contraseña, Nombre_completo, Rol, Telefono, Ciudad, Direccion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, contraseña);
            stmt.setString(4, nombreCompleto);
            stmt.setString(5, rol);
            stmt.setString(6, telefono);
            stmt.setString(7, ciudad);
            stmt.setString(8, direccion);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para editar un empleado
    public boolean editarEmpleado(int id, String nombre, String contraseña, String nombreCompleto,
                                  String rol, String telefono, String ciudad, String direccion) {
        String query = "UPDATE Usuario SET Nombre_usuario = ?, Contraseña = ?, Nombre_completo = ?, " +
                "Rol = ?, Telefono = ?, Ciudad = ?, Direccion = ? WHERE id_Usuario = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, contraseña);
            stmt.setString(3, nombreCompleto);
            stmt.setString(4, rol);
            stmt.setString(5, telefono);
            stmt.setString(6, ciudad);
            stmt.setString(7, direccion);
            stmt.setInt(8, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un empleado
    public boolean eliminarEmpleado(int id) {
        String query = "DELETE FROM Usuario WHERE id_Usuario = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todos los empleados
    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT * FROM Usuario";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id_Usuario");
                String nombre = rs.getString("Nombre_usuario");
                String contraseña = rs.getString("Contraseña");
                String nombreCompleto = rs.getString("Nombre_completo");
                String rol = rs.getString("Rol");
                String telefono = rs.getString("Telefono");
                String ciudad = rs.getString("Ciudad");
                String direccion = rs.getString("Direccion");

                Empleado empleado = new Empleado(id, nombre, contraseña, nombreCompleto, rol, telefono, ciudad, direccion);
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleados;
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