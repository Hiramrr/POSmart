package BaseDatos;

import Interfaz_DAO.AgregarEmpleado_DAO_Interface;

import java.sql.*;

public class AgregarEmpleado_DAO implements AgregarEmpleado_DAO_Interface {
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
    public boolean agregarEmpleado(int id, String nombre, String contraseña, String nombreCompleto, String rol, String telefono, String ciudad, String direccion) {
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
}
