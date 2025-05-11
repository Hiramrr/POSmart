package BaseDatos;

import Interfaz_DAO.GestionEmpleado_DAO_Interface;
import controllers.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionEmpleado_DAO implements GestionEmpleado_DAO_Interface {
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
    public boolean editarEmpleado(int id, String nombre, String contraseña, String nombreCompleto, String rol, String telefono, String ciudad, String direccion) {
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

    @Override
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

    @Override
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
}
